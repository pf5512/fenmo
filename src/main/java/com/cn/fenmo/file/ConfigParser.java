package com.cn.fenmo.file;

import java.util.Properties;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConfigParser extends DefaultHandler
{
  private Properties props;
  private String currentName;
  private StringBuffer currentValue = new StringBuffer();

  public ConfigParser()
  {
    this.props = new Properties();
  }

  public Properties getProps() {
    return this.props;
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException
  {
    this.currentValue.delete(0, this.currentValue.length());
    this.currentName = qName;
  }

  public void characters(char[] ch, int start, int length)
    throws SAXException
  {
    this.currentValue.append(ch, start, length);
  }

  public void endElement(String uri, String localName, String qName)
    throws SAXException
  {
    this.props.put(qName.toLowerCase(), this.currentValue.toString().trim());
  }
}