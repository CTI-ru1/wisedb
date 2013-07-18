package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Notifies observers with the last NodeReading.
 */
public final class LastNodeReadingConsumer {

    /**
     * Static logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastNodeReadingConsumer.class);

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingConsumer ourInstance = null;

    /**
     * The QUEUE.
     */
    private static final BlockingQueue<NodeReading> QUEUE = new LinkedBlockingQueue<NodeReading>();

    /**
     * Listeners double HashMap<NodeID-CapabilityID, Listener>>.
     */
    private final HashMap<String, List<AbstractNodeReadingListener>> listeners;

    /**
     *
     */
    private AbstractNodeReadingListener virtualReadingListener;

    public AbstractNodeReadingListener getVirtualReadingListener() {
        return virtualReadingListener;
    }

    /**
     * Used to lock specific block.
     */
    private final Object lock = new Object();

    /**
     * Private constructor suppresses generation of a (public) default constructor.
     */
    private LastNodeReadingConsumer() {
        final NodeReadingDistributer nodeReadingDistributer = new NodeReadingDistributer(QUEUE);
        nodeReadingDistributer.start();
        listeners = new HashMap<String, List<AbstractNodeReadingListener>>();
    }

    /**
     * LastNodeReadingConsumer is loaded on the first execution of LastNodeReadingConsumer.getInstance()
     * or the first access to LastNodeReadingConsumer.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LastNodeReadingConsumer getInstance() {
        synchronized (LastNodeReadingConsumer.class) {
            if (ourInstance == null) {
                ourInstance = new LastNodeReadingConsumer();
            }
        }
        return ourInstance;
    }

    /**
     * Register a new Listener for a specific capability.
     *
     * @param nodeId       the node ID
     * @param capabilityID the capability ID
     * @param listener     the new Listener
     */
    public void registerListener(final String nodeId, final String capabilityID,
                                 final AbstractNodeReadingListener listener) {
        synchronized (lock) {
            LOGGER.info("registerListener(before):" + listeners.size());
            final String key = nodeId + "--" + capabilityID;
            if (listeners.containsKey(key)) {
                listeners.get(key).add(listener);
            } else {
                listeners.put(key, new ArrayList<AbstractNodeReadingListener>());
                listeners.get(key).add(listener);
            }
            LOGGER.info("registerListener(after):" + listeners.size());
        }
    }

    public void registerVirtualReadingListener(final AbstractNodeReadingListener listener) {
        synchronized (lock) {
            virtualReadingListener = listener;
        }
    }

    /**
     * Remove the specified listener.
     *
     * @param nodeId       the Node ID
     * @param capabilityID the Capability ID
     */
    public void removeListener(final String nodeId, final String capabilityID, final AbstractNodeReadingListener listener) {

        synchronized (lock) {
            LOGGER.info("removeListener(before):" + listeners.size());
            final String key = nodeId + "--" + capabilityID;
            if (listeners.containsKey(key)) {
                listeners.get(key).remove(listener);
                if (listeners.get(key).isEmpty()) {
                    listeners.remove(key);
                }
            }
            LOGGER.info("removeListener(after):" + listeners.size());
        }
    }

    /**
     * Return an ArrayList if the listeners map contains the specific key.
     *
     * @param nodeID       the node id
     * @param capabilityID the key
     * @return true if the map contains the key
     */
    protected boolean listenersContains(final String nodeID, final String capabilityID, final String nodeUrnPrefix, final String capabilityUrnPrefix) {
//
//        for (String ket : listeners.keySet()) {
//            LOGGER.info(ket);
//        }
        boolean hasKey = false;
        String[] keys = new String[]{
                nodeID + "--" + capabilityID, //node-capability specific
                nodeUrnPrefix + "*--" + capabilityID, //testbed-capability specific
                nodeUrnPrefix + "*--*", //testbed specific
                nodeID + "--" + capabilityUrnPrefix + "*", //testbed-node specific
                "*--" + capabilityID, //capability specific
                nodeID + "--*", //node specific
                "*--*" //all
        };
        synchronized (LastNodeReadingConsumer.class) {

            for (String key : keys) {
                hasKey |= listeners.containsKey(key);
            }
        }

        return hasKey;
    }

    /**
     * Returns the listeners of a specific node and capability .
     *
     * @param nodeID       the node id
     * @param capabilityID the key
     * @return an AbstractNodeReadingListener
     */
    protected List<AbstractNodeReadingListener> getListener(final String nodeID, final String capabilityID, final String nodeUrnPrefix, final String capabilityUrnPrefix) {
        // a temporary array buffer
        String[] keys = new String[]{
                nodeID + "--" + capabilityID, //node-capability specific
                nodeUrnPrefix + "*--" + capabilityID, //testbed-capability specific
                nodeUrnPrefix + "*--*", //testbed specific
                nodeID + "--" + capabilityUrnPrefix + "*", //testbed-node specific
                "*--" + capabilityID, //capability specific
                nodeID + "--*", //node specific
                "*--*" //all
        };
        final List<AbstractNodeReadingListener> listenersList = new ArrayList<AbstractNodeReadingListener>();
        synchronized (LastNodeReadingConsumer.class) {
            for (String key : keys) {
                if (listeners.containsKey(key)) {
                    listenersList.addAll(listeners.get(key));
                }
            }
        }

        return listenersList;
    }

    /**
     * Forwards the NodeReading to the Distributer Thread.
     *
     * @param thisReading the NodeReading
     */
    public void addNodeReading(final NodeReading thisReading) {
        QUEUE.offer(thisReading);
    }
}


