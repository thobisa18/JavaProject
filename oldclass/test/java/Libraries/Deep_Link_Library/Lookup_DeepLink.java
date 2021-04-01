package Libraries.Deep_Link_Library;

public class Lookup_DeepLink
{
 String msg_Preview;
 String msg_Text;
 String msg_Displayed;
 String msg_heading;
 String msg_label;
 String deepLink_uiid;

 public Lookup_DeepLink(String deepLink_uiid,String msg_heading,String msg_Preview,String msg_Text,String msg_label,String msg_Displayed)
 {
   this.msg_Preview=msg_Preview;
   this.msg_Text=msg_Text;
   this.msg_Displayed=msg_Displayed;
   this.deepLink_uiid=deepLink_uiid;
   this.msg_label=msg_label;
   this.msg_heading=msg_heading;
 }

  public String getMsg_Preview() {
    return msg_Preview;
  }

  public String getMsg_Displayed() {
    return msg_Displayed;
  }

  public String getDeepLink_uiid() {
    return deepLink_uiid;
  }

  public String getMsg_Text() {
    return msg_Text;
  }

  public String getMsg_heading() {
    return msg_heading;
  }

  public String getMsg_label() {
    return msg_label;
  }
}
