// This file contains material supporting section 10.9 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

/*
 * SimpleClient.java   2001-02-08
 *
 * Copyright (c) 2001 Robert Laganiere and Timothy C. Lethbridge.
 * All Rights Reserved.
 *
 */
package db;

import java.awt.List;
import java.awt.Color;

import javax.swing.JTextArea;

public class SimpleClient extends AbstractClient
{
  private List liste;
  private JTextArea textArea;

  public SimpleClient(List liste)
  {
    super("localhost",12345);
    this.liste = liste;
  }

  public SimpleClient(int port, List liste)
  {
    super("localhost",port);
    this.liste = liste;
  }

  public SimpleClient(String host, int port,JTextArea text)
  {
    super(host,port);
    textArea = text;
  }

  /**
   * Hook method called after the connection has been closed.
   */
  protected void connectionClosed()
  {
    liste.add("**Connection closed**");
    textArea.setBackground(Color.red);
  }

  /**
   * Hook method called each time an exception is thrown by the
   * client's thread that is waiting for messages from the server.
   *
   * @param exception the exception raised.
   */
  protected void connectionException(Exception exception)
  {
    textArea.append("**Connection exception: " + exception);
    textArea.setBackground(Color.red);
  }

  /**
   * Hook method called after a connection has been established.
   */
  protected void connectionEstablished()
  {
	textArea.append("--Connection established");
    textArea.setBackground(Color.green);
  }

  /**
   * Handles a message sent from the server to this client.
   *
   * @param msg   the message sent.
   */
  protected void handleMessageFromServer(Object msg)
  {
    textArea.append(msg.toString());
  }
}
