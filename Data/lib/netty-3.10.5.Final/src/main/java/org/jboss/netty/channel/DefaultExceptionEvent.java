/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.channel;

import static org.jboss.netty.channel.Channels.*;

/**
 * The default {@link ExceptionEvent} implementation.
 */
public class DefaultExceptionEvent implements ExceptionEvent {

    private final Channel channel;
    private final Throwable cause;

    /**
     * Creates a new instance.
     */
    public DefaultExceptionEvent(Channel channel, Throwable cause) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (cause == null) {
            throw new NullPointerException("cause");
        }
        this.channel = channel;
        this.cause = cause;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelFuture getFuture() {
        return succeededFuture(getChannel());
    }

    public Throwable getCause() {
        return cause;
    }

    @Override
    public String toString() {
        return getChannel().toString() + " EXCEPTION: " + cause;
    }
}
