package io.ketlv.ediplomadapp.services.fabric;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.enumuration.ModeOfStudyEnum;
import io.ketlv.ediplomadapp.enumuration.SexEnum;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainCode {
    private static Logger log = LoggerFactory.getLogger(ChainCode.class);

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    public static Gateway connectToNetwork(String configPath, String username) throws IOException {
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

    public static void issueDiploma(Diploma diploma, String configPathOrg) throws Exception {
        // enrolls the admin and registers the user
        try {
            EnrollAdmin.execute("issuer.com", "1054", 1);
            RegisterUser.execute("issuer.com", "1054", 1, diploma.getStudentId());
        } catch (Exception e) {
            System.err.println(e);
        }

        // connect to the network and invoke the smart contract
        try (Gateway gateway = connectToNetwork(configPathOrg, diploma.getStudentId())) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("e-diploma");


            contract.submitTransaction("issueDiploma", diploma.getGraduationCatalogId().toString(), diploma.getMajorId(),
                    diploma.getStudentId(), diploma.getFullName(), diploma.getDateOfBirth().toString(), diploma.getPlaceOfOrigin(),
                    diploma.getSex().toString(), diploma.getIndigenousId().toString(), diploma.getNationalityId().toString(), diploma.getRankingId().toString(),
                    diploma.getYearGraduation().toString(), diploma.getModeOfStudy().getValue(), diploma.getDonviSymbol(),
                    diploma.getSerialNumber(), diploma.getReferenceNumber(), diploma.getSigner(), diploma.getSignerTitle(),
                    diploma.getForeignLanguage() != null ? diploma.getForeignLanguage() : "",
                    diploma.getLevelForeignLanguage() != null ? diploma.getLevelForeignLanguage() : "",
                    diploma.getDateOfEnrollment() != null ? diploma.getDateOfEnrollment().toString() : "",
                    diploma.getDateOfGraduation() != null ? diploma.getDateOfGraduation().toString() : "",
                    diploma.getTrainingCourse().toString(),
                    diploma.getDateOfDefend() != null ? diploma.getDateOfDefend().toString() : "",
                    diploma.getHoiDongThi() != null ? diploma.getHoiDongThi() : "",
                    diploma.getDecisionNumber() != null ? diploma.getDecisionNumber() : "",
                    diploma.getDecisionEstablishingCouncil() != null ? diploma.getDecisionEstablishingCouncil() : "",
                    diploma.getReqTypeId().toString(), diploma.getGpa() != null ? diploma.getGpa().toString() : "",
                    diploma.getDiplomaTypeSymbol(),
                    diploma.getTrainingPeriodSemester() != null ? diploma.getTrainingPeriodSemester().toString() : "",
                    diploma.getTotalCredits() != null ? diploma.getTotalCredits().toString() : "",
                    diploma.getDiplomaLink() != null ? diploma.getDiplomaLink() : "",
                    diploma.getNote() != null ? diploma.getNote() : "", diploma.getStatus().toString(),
                    diploma.getCreatedBy() != null ? diploma.getCreatedBy() : "", diploma.getCreatedDate().toString(),
                    diploma.getLastModifiedDate().toString());

//            System.out.println(result);
        }
        catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }

    }
}
