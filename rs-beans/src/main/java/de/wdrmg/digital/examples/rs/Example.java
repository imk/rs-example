package de.wdrmg.digital.examples.rs;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Example {
  @XmlAttribute
  private String key;
  @XmlValue
  private String value;
  @XmlTransient
  private String ignored;

  public Example() {}

  public Example(final String key, final String value, final String ignored) {
    this.key = key;
    this.value = value;
    this.ignored = ignored;
  }

  public final String getKey() {
    return key;
  }

  public final void setKey(final String key) {
    this.key = key;
  }

  public final String getValue() {
    return value;
  }

  public final void setValue(final String value) {
    this.value = value;
  }

  public final String getIgnored() {
    return ignored;
  }

  public final void setIgnored(final String ignored) {
    this.ignored = ignored;
  }

  @Override
  public String toString() {
    return "Example [key=" + key + ", value=" + value + ", ignored=" + ignored + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ignored == null) ? 0 : ignored.hashCode());
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Example other = (Example) obj;
    if (ignored == null) {
      if (other.ignored != null) {
        return false;
      }
    } else if (!ignored.equals(other.ignored)) {
      return false;
    }
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    } else if (!key.equals(other.key)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  @XmlRootElement(name = "examples")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Wrapper {
    @XmlElement(name = "example")
    private List<Example> elements;

    public Wrapper() {

    }

    public Wrapper(final List<Example> elements) {
      this.elements = elements;
    }

    public final List<Example> getElements() {
      return elements;
    }

    public final void setElements(final List<Example> elements) {
      this.elements = elements;
    }

    @Override
    public String toString() {
      return "Wrapper [elements=" + elements + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((elements == null) ? 0 : elements.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Wrapper other = (Wrapper) obj;
      if (elements == null) {
        if (other.elements != null) {
          return false;
        }
      } else if (!elements.equals(other.elements)) {
        return false;
      }
      return true;
    }
  }
}
