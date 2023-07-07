package io.ketlv.ediplomadapp.event;

import io.ketlv.ediplomadapp.domain.Diploma;
import lombok.Getter;
import org.hyperledger.fabric.gateway.Contract;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubmitDiplomaTransactionEvent extends ApplicationEvent {
    private Diploma diploma;
    private String name;
    private Contract contract;

    public SubmitDiplomaTransactionEvent(Object source, Diploma diploma, String name, Contract contract) {
        super(source);
        this.diploma = diploma;
        this.name = name;
        this.contract = contract;
    }
}
