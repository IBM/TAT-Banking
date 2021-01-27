/*##############################################################################
# Copyright 2021 IBM Corp. All Rights Reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
##############################################################################*/
package tat.microservice.bank.account.cmd.es.sagas;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tat.microservice.bank.account.cmd.es.commands.DebitSourceAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.MarkTransferCompletedCommand;
import tat.microservice.bank.account.cmd.es.commands.MarkTransferFailedCommand;
import tat.microservice.bank.account.cmd.es.commands.ReturnMoneyOfFailedTransferCommand;
import tat.microservice.bank.account.cmd.es.commands.TransferCreditDestinationAccountCommand;
import tat.microservice.bank.account.cmd.es.events.TransferCreatedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferDestinationAccountCreditedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferDestinationAccountNotFoundEvent;
import tat.microservice.bank.account.cmd.es.events.TransferSourceAccountDebitRejectedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferSourceAccountDebitedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferSourceAccountNotFoundEvent;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Saga
public class AccountTransferManagementSaga {
	private final Logger logger = LoggerFactory.getLogger(getClass());

    private transient CommandBus commandBus;

    @Autowired
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    private String fromAccountId;
    private String toAccountId;
    private double amount;

    @StartSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(TransferCreatedEvent event) {
    	
    	logger.info(" <============  on(TransferCreatedEvent event)  =============>" +event);
        this.fromAccountId = event.getFromAccountId();
        this.toAccountId = event.getToAccountId();
        this.amount = event.getAmount();

        DebitSourceAccountCommand command = new DebitSourceAccountCommand(event.getFromAccountId(),
                                                                                  event.getTransferId(),
                                                                                  event.getAmount());
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
        logger.info(" <============  on(TransferCreatedEvent event)  =============>" +event.toString());
    }
 
    // @EndSaga 
    @SagaEventHandler(associationProperty = "transferId")
    public void on(TransferSourceAccountNotFoundEvent event) {
    	logger.info(" <============  on(TransferSourceAccountNotFoundEvent event)  =============>" +event);
        MarkTransferFailedCommand markFailedCommand = new MarkTransferFailedCommand(event.getTransferId());
        commandBus.dispatch(asCommandMessage(markFailedCommand), LoggingCallback.INSTANCE);
        logger.info(" <============ on(TransferSourceAccountNotFoundEvent event)  =============>" +event.toString());
    }
    // @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(TransferSourceAccountDebitRejectedEvent event) {
    	logger.info(" <============  on(TransferSourceAccountDebitRejectedEvent event)  =============>" +event);
        MarkTransferFailedCommand markFailedCommand = new MarkTransferFailedCommand(event.getTransferId());
        commandBus.dispatch(asCommandMessage(markFailedCommand), LoggingCallback.INSTANCE);
        logger.info(" <============  on(TransferSourceAccountDebitRejectedEvent event)  =============>" +event.toString());
    }

    @SagaEventHandler(associationProperty = "transferId")
    public void on(TransferSourceAccountDebitedEvent event) {
    	logger.info(" <============ on(TransferSourceAccountDebitedEvent event) =============>" +event);
        TransferCreditDestinationAccountCommand command = new TransferCreditDestinationAccountCommand(toAccountId,  event.getTransferId(), event.getAmount());
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
        logger.info(" <============  on(TransferSourceAccountDebitedEvent event) =============>" +event.toString());
    }

    // @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(TransferDestinationAccountNotFoundEvent event) {
    	logger.info(" <============  on(TransferDestinationAccountNotFoundEvent event)  =============>" +event);
        ReturnMoneyOfFailedTransferCommand returnMoneyCommand = new ReturnMoneyOfFailedTransferCommand(fromAccountId, amount);
        commandBus.dispatch(asCommandMessage(returnMoneyCommand), LoggingCallback.INSTANCE);

        MarkTransferFailedCommand markFailedCommand = new MarkTransferFailedCommand(event.getTransferId());
        commandBus.dispatch(asCommandMessage(markFailedCommand), LoggingCallback.INSTANCE);
        logger.info(" <============  on(TransferDestinationAccountNotFoundEvent event)  =============>" +event.toString());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(TransferDestinationAccountCreditedEvent event) {
    	logger.info(" <============  on(TransferDestinationAccountCreditedEvent event)  =============>" +event);
        MarkTransferCompletedCommand command = new MarkTransferCompletedCommand(event.getTransferId());
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
        logger.info(" <============  on(TransferDestinationAccountCreditedEvent event)  =============>" +event.toString());
    }
}
