package io.ketlv.ediplomadapp.services.fabric;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starkbank.ellipticcurve.Ecdsa;
import com.starkbank.ellipticcurve.PrivateKey;
import com.starkbank.ellipticcurve.PublicKey;
import com.starkbank.ellipticcurve.Signature;
import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.event.SubmitDiplomaTransactionEvent;
import io.ketlv.ediplomadapp.mapper.DiplomaMapper;
import io.ketlv.ediplomadapp.services.dto.DiplomaQueryRes;
import io.ketlv.ediplomadapp.services.dto.DiplomaResSearch;
import org.hyperledger.fabric.gateway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ChainCode {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private DiplomaMapper diplomaMapper;

    private static Logger log = LoggerFactory.getLogger(ChainCode.class);

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
    }
    public Gateway connectToNetwork(String configPath, String username) throws IOException {
        try {// Load a file system based wallet for managing identities.
            Path walletPath = Paths.get("wallet");
            Wallet wallet = Wallets.newFileSystemWallet(walletPath);
            // load a CCP
            Path networkConfigPath = Paths.get(configPath);

            Gateway.Builder builder = Gateway.createBuilder();
            builder.identity(wallet, username).networkConfig(networkConfigPath).discovery(true);
            return builder.connect();
        } catch (Exception e) {
            log.error(String.format("[ERROR]: %s", e.getMessage()));
            throw e;
        }
    }

    @Async
    public void issueDiploma(List<Diploma> diplomas, String configPathOrg, String userId) throws Exception {
        // enrolls the admin and registers the user
        try {
            EnrollAdmin.execute("issuer.com", "1054", 1);
            RegisterUser.execute("issuer.com", "1054", 1, userId);
        } catch (Exception e) {
            System.err.println(e);
        }
        try(Gateway gateway = connectToNetwork(configPathOrg, userId);) {
            // get the network and contract
            Network network = gateway.getNetwork("e-channel");
            Contract contract = network.getContract("e-diploma");
            for (Diploma dip : diplomas) {
                applicationEventPublisher.publishEvent(new SubmitDiplomaTransactionEvent(this, dip, "issueDiploma", contract));
            }
            Thread.sleep(20000);
        } catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
        // connect to the network and invoke the smart contract

    }

    public void compareDataBlockchain(List<DiplomaResSearch> diplomaDtos, String configPathOrg, String userId) {
        try(Gateway gateway = connectToNetwork(configPathOrg, userId);) {
            // get the network and contract
            Network network = gateway.getNetwork("e-channel");
            Contract contract = network.getContract("e-diploma");

            PrivateKey privateKey = new PrivateKey();
            PublicKey publicKey = privateKey.publicKey();

            diplomaDtos.stream().map(diplomaDto -> {
                try {
                    byte[] result = contract.evaluateTransaction("queryDiplomaBySerialNumber", diplomaDto.getSerialNumber().trim());
                    ObjectMapper objectMapper = new ObjectMapper();
                    DiplomaQueryRes object = objectMapper.readValue(new String(result), DiplomaQueryRes.class);
                    // Generate Signature
                    Signature signature = Ecdsa.sign(object.getRecord().toString(), privateKey);

                    // Verify if signature is valid
                    boolean verified = Ecdsa.verify(diplomaDto.toString(), signature, publicKey) ;
                    diplomaDto.setStatusValidate(verified);
                    // Return the signature verification status
                } catch (ContractException e) {
                    e.printStackTrace();
                    diplomaDto.setStatusValidate(false);
                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return diplomaDto;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDiploma(String serialNumber, String status, String diplomaLink, String configPathOrg, String userId) {
        try(Gateway gateway = connectToNetwork(configPathOrg, userId);) {
            Network network = gateway.getNetwork("e-channel");
            Contract contract = network.getContract("e-diploma");
            Transaction transaction = contract.createTransaction("updateDiploma");
            byte[] res = transaction.submit(serialNumber, status, diplomaLink);
            diplomaMapper.updateTransaction(serialNumber, transaction.getTransactionId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ContractException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
