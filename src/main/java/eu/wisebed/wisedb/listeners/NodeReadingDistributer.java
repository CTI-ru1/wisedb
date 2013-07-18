package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * NodeReadingDistributer class.
 */
public final class NodeReadingDistributer extends Thread {

    /**
     * Static logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeReadingDistributer.class);

    /**
     * The queue.
     */
    private final BlockingQueue<NodeReading> queue;

    /**
     * Enables/Disables this Thread.
     */
    private boolean isEnabled;

    /**
     * Default Constructor.
     *
     * @param thisQueue a BlockingQueue
     */
    public NodeReadingDistributer(final BlockingQueue<NodeReading> thisQueue) {
        this.queue = thisQueue;
        isEnabled = true;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see Thread#Thread(ThreadGroup, Runnable, String)
     */
    public void run() {
        while (isEnabled) {
            try {
                final NodeReading lastReading = queue.take();
                // LOGGER.info("New Reading: " + lastReading.toString());
                final String nodeName = lastReading.getCapability().getNode().getName();
                final String capabilityName = lastReading.getCapability().getCapability().getName();
                String testbedUrnPrefix = "";
                if (nodeName.contains(":")) {
                    testbedUrnPrefix = nodeName.substring(0, nodeName.lastIndexOf(":") + 1);
                }
                String capabilityUrnPrefix = "";
                if (capabilityName.contains(":")) {
                    capabilityUrnPrefix = capabilityName.substring(0, nodeName.lastIndexOf(":") + 1);
                }
                if (LastNodeReadingConsumer.getInstance().listenersContains(
                        nodeName,
                        capabilityName,
                        testbedUrnPrefix,
                        capabilityUrnPrefix)) {
                    LOGGER.info("Updating.... : " + nodeName + "-" + capabilityName + "@" + lastReading);
                    List<AbstractNodeReadingListener> list = LastNodeReadingConsumer.getInstance().getListener(
                            nodeName,
                            capabilityName,
                            testbedUrnPrefix,
                            capabilityUrnPrefix
                    );
                    for (AbstractNodeReadingListener listener : list) {
                        LOGGER.info("updating " + listener.toString());
                        listener.update(lastReading);
                    }

                }
                try {
                    if (lastReading.getCapability().getNode().getName().contains("virtual")) {
                        LastNodeReadingConsumer.getInstance().getVirtualReadingListener().update(lastReading);
                    }
                } catch (Exception e) {
                    LOGGER.error(e, e);
                }

            } catch (final InterruptedException e) {
                LOGGER.fatal("Interrupted Exception ", e);
            }
        }
    }
}
