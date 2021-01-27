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
package tat.microservice.bank.account.cmd.es.configuration;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Autowired
	private Environment environment;

	/*
	 * @Override public void configureMessageBroker(MessageBrokerRegistry config) {
	 * if (ArrayUtils.contains(environment.getActiveProfiles(),
	 * "distributed-command-bus")) {
	 * config.enableStompBrokerRelay("/topic").setRelayHost("rabbitmq"); } else {
	 * config.enableSimpleBroker("/topic"); }
	 * config.setApplicationDestinationPrefixes("/app"); }
	 */

	
	  public void configureMessageBroker(MessageBrokerRegistry config) {
		
	  config.setApplicationDestinationPrefixes("/app");
	  
	  // Use this for enabling a Full featured broker like RabbitMQ
	  
	  config.enableStompBrokerRelay("/topic") .setRelayHost("rabbit")
	  .setRelayPort(61613) .setClientLogin("guest") .setClientPasscode("guest");
	  
	  
	  config.enableStompBrokerRelay("/topic") .setRelayHost("rabbit")
	  .setRelayPort(61613) .setClientLogin("amq-broker")
	  .setClientPasscode("amq-broker"); }
	 

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").withSockJS();
	}
}