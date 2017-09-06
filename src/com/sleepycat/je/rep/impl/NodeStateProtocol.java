/*-
 * Copyright (C) 2002, 2017, Oracle and/or its affiliates. All rights reserved.
 *
 * This file was distributed by Oracle as part of a version of Oracle Berkeley
 * DB Java Edition made available at:
 *
 * http://www.oracle.com/technetwork/database/database-technologies/berkeleydb/downloads/index.html
 *
 * Please see the LICENSE file included in the top-level directory of the
 * appropriate version of Oracle Berkeley DB Java Edition for a copy of the
 * license and additional information.
 */
package com.sleepycat.je.rep.impl;

import com.sleepycat.je.rep.ReplicatedEnvironment.State;
import com.sleepycat.je.rep.impl.node.NameIdPair;
import com.sleepycat.je.rep.net.DataChannelFactory;

/**
 * Defines the protocol used in support of node state querying. The message
 * request sequence: NODE_STATE_REQ -> NODE_STATE_RESP
 */
public class NodeStateProtocol extends TextProtocol {

    public static final String VERSION         = "1.0";

    /* The messages defined by this class. */
    public final MessageOp     NODE_STATE_REQ  = new MessageOp("STATEREQ", NodeStateRequest.class);
    public final MessageOp     NODE_STATE_RESP = new MessageOp("STATERESP", NodeStateResponse.class);

    public NodeStateProtocol(String groupName, NameIdPair nameIdPair, RepImpl repImpl,
                             DataChannelFactory channelFactory) {

        super(VERSION, groupName, nameIdPair, repImpl, channelFactory);

        this.initializeMessageOps(new MessageOp[] { NODE_STATE_REQ, NODE_STATE_RESP });

        setTimeouts(repImpl, RepParams.REP_GROUP_OPEN_TIMEOUT, RepParams.REP_GROUP_READ_TIMEOUT);
    }

    /* Message request the state of the specified node. */
    public class NodeStateRequest extends RequestMessage {
        private final String nodeName;

        public NodeStateRequest(String nodeName) {
            this.nodeName = nodeName;
        }

        public NodeStateRequest(String line, String[] tokens) throws InvalidMessageException {

            super(line, tokens);
            nodeName = nextPayloadToken();
        }

        public String getNodeName() {
            return nodeName;
        }

        @Override
        public MessageOp getOp() {
            return NODE_STATE_REQ;
        }

        @Override
        protected String getMessagePrefix() {
            return messagePrefixNocheck;
        }

        @Override
        public String wireFormat() {
            return wireFormatPrefix() + SEPARATOR + nodeName;
        }
    }

    /* Message return state of specified node. */
    public class NodeStateResponse extends ResponseMessage {
        private final String nodeName;
        private final String masterName;
        private final long   joinTime;
        private final State  nodeState;

        public NodeStateResponse(String nodeName, String masterName, long joinTime, State nodeState) {
            this.nodeName = nodeName;
            this.masterName = masterName;
            this.joinTime = joinTime;
            this.nodeState = nodeState;
        }

        public NodeStateResponse(String line, String[] tokens) throws InvalidMessageException {

            super(line, tokens);
            nodeName = nextPayloadToken();
            masterName = nextPayloadToken();
            joinTime = Long.parseLong(nextPayloadToken());
            nodeState = State.valueOf(nextPayloadToken());
        }

        public String getNodeName() {
            return nodeName;
        }

        public String getMasterName() {
            return masterName;
        }

        public long getJoinTime() {
            return joinTime;
        }

        public State getNodeState() {
            return nodeState;
        }

        @Override
        public MessageOp getOp() {
            return NODE_STATE_RESP;
        }

        @Override
        protected String getMessagePrefix() {
            return messagePrefixNocheck;
        }

        @Override
        public String wireFormat() {
            return wireFormatPrefix() + SEPARATOR + nodeName + SEPARATOR + masterName + SEPARATOR
                    + Long.toString(joinTime) + SEPARATOR + nodeState.toString();
        }
    }
}
