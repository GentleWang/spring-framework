/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.messaging.core;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.util.Assert;

/**
 * Base class for a messaging template that can resolve String-based destinations.
 *
 * @author Mark Fisher
 * @author Rossen Stoyanchev
 * @since 4.0
 */
public abstract class AbstractDestinationResolvingMessagingTemplate<D> extends
		AbstractMessagingTemplate<D> implements
		DestinationResolvingMessageSendingOperations<D>,
		DestinationResolvingMessageReceivingOperations<D>,
		DestinationResolvingMessageRequestReplyOperations<D> {

	private volatile DestinationResolver<D> destinationResolver;


	public void setDestinationResolver(DestinationResolver<D> destinationResolver) {
		this.destinationResolver = destinationResolver;
	}


	@Override
	public <P> void send(String destinationName, Message<P> message) {
		D destination = resolveDestination(destinationName);
		this.doSend(destination, message);
	}

	protected final D resolveDestination(String destinationName) {
		Assert.notNull(destinationResolver, "destinationResolver is required when passing a name only");
		return this.destinationResolver.resolveDestination(destinationName);
	}

	@Override
	public <T> void convertAndSend(String destinationName, T payload) {
		Map<String, Object> headers = null;
		this.convertAndSend(destinationName, payload, headers);
	}

	@Override
	public <T> void convertAndSend(String destinationName, T payload, Map<String, Object> headers) {
		MessagePostProcessor postProcessor = null;
		this.convertAndSend(destinationName, payload, headers, postProcessor);
	}

	@Override
	public <T> void convertAndSend(String destinationName, T payload, MessagePostProcessor postProcessor) {
		Map<String, Object> headers = null;
		this.convertAndSend(destinationName, payload, headers, postProcessor);
	}

	@Override
	public <T> void convertAndSend(String destinationName, T payload, Map<String, Object> headers,
			MessagePostProcessor postProcessor) {

		D destination = resolveDestination(destinationName);
		super.convertAndSend(destination, payload, headers, postProcessor);
	}

	@Override
	public <P> Message<P> receive(String destinationName) {
		D destination = resolveDestination(destinationName);
		return super.receive(destination);
	}

	@Override
	public <T> T receiveAndConvert(String destinationName, Class<T> targetClass) {
		D destination = resolveDestination(destinationName);
		return super.receiveAndConvert(destination, targetClass);
	}

	@Override
	public Message<?> sendAndReceive(String destinationName, Message<?> requestMessage) {
		D destination = resolveDestination(destinationName);
		return super.sendAndReceive(destination, requestMessage);
	}

	@Override
	public <T> T convertSendAndReceive(String destinationName, Object request, Class<T> targetClass) {
		D destination = resolveDestination(destinationName);
		return super.convertSendAndReceive(destination, request, targetClass);
	}

	@Override
	public <T> T convertSendAndReceive(String destinationName, Object request, Map<String, Object> headers,
			Class<T> targetClass) {

		D destination = resolveDestination(destinationName);
		return super.convertSendAndReceive(destination, request, headers, targetClass);
	}

	@Override
	public <T> T convertSendAndReceive(String destinationName, Object request, Class<T> targetClass,
			MessagePostProcessor postProcessor) {

		D destination = resolveDestination(destinationName);
		return super.convertSendAndReceive(destination, request, targetClass, postProcessor);
	}

	@Override
	public <T> T convertSendAndReceive(String destinationName, Object request, Map<String, Object> headers,
			Class<T> targetClass, MessagePostProcessor postProcessor) {

		D destination = resolveDestination(destinationName);
		return super.convertSendAndReceive(destination, request, headers, targetClass, postProcessor);
	}

}
