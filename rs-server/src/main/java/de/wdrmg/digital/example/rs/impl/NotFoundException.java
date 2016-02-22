package de.wdrmg.digital.example.rs.impl;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class NotFoundException extends RuntimeException {
  private static final long serialVersionUID = -3822703179260319585L;

  public NotFoundException(final String message) {
    super(message);
  }

  public Message getMessageEntity() {
    return new Message(Response.Status.NOT_FOUND.getStatusCode(), getMessage());
  }

  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Message {
    @XmlAttribute
    private int status;
    @XmlElement
    private String message;

    public Message() {}

    public Message(final int status, final String message) {
      super();
      this.status = status;
      this.message = message;
    }

    public final int getStatus() {
      return status;
    }

    public final void setStatus(final int status) {
      this.status = status;
    }

    public final String getMessage() {
      return message;
    }

    public final void setMessage(final String message) {
      this.message = message;
    }
  }
}
