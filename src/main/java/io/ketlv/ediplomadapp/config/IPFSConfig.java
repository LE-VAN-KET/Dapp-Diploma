package io.ketlv.ediplomadapp.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IPFSConfig {

    public IPFS ipfs;

    public IPFSConfig() {
        ipfs = new IPFS("127.0.0.1", 5001);
    }

}
