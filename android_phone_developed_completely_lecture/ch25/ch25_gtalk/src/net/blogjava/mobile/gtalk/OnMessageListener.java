package net.blogjava.mobile.gtalk;

import org.jivesoftware.smack.packet.Message;

public interface OnMessageListener
{
    public void processMessage(Message message);
}
