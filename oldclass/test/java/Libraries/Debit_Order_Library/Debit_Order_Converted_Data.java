package Libraries.Debit_Order_Library;

public class Debit_Order_Converted_Data {
  private String  data;
  private String ExpectedStep, PreviousStep;


  String Prompt;
  String Test_CaSe_ID;
  String Test_Case_Name;
  String Utterance;
  String Confirm_Itent;
  String ID_Number;
  String Smart_In_Contact;
  String Card_Number;
  String Pin_Number;
  String Reverse_Stop;
  String Marchent;
  String Multiple;
  String Skip_terms;
  String Confirm_Terms;
  String NumberTransferedTo;
  String disputeReason;
  String Confirm_Devive;
  String Intent;
  String DeepLnkUI_ID;
  String AfterIntent;
  String segment;

  public String getTest_Case_Name() {
    return Test_Case_Name;
  }

  public String getNumberTransferedTo() {
    return NumberTransferedTo;
  }

  public Debit_Order_Converted_Data(String Test_CaSe_ID,String Test_Case_Name,String Utterance,String Confirm_Itent,
      String ID_Number, String Smart_In_Contact, String Card_Number,String Pin_Number,String Reverse_Stop,
      String Marchent,String Multiple, String Skip_terms,String Confirm_Terms,String NumberTransferedTo,String prompt,String disputeReason)
  {
    this.Prompt=prompt;
    this.NumberTransferedTo=NumberTransferedTo;
    this.Test_CaSe_ID=Test_CaSe_ID;
    this.Test_Case_Name=Test_Case_Name;
    this.Utterance=Utterance;
    this.Confirm_Itent=Confirm_Itent;
    this.ID_Number=ID_Number;
    this.Smart_In_Contact=Smart_In_Contact;
    this.Card_Number=Card_Number;
    this.Pin_Number=Pin_Number;
    this.Reverse_Stop=Reverse_Stop;
    this.Marchent=Marchent;
    this.Multiple=Multiple;
    this.Skip_terms=Skip_terms;
    this.Confirm_Terms=Confirm_Terms;
    this.disputeReason=disputeReason;
  }

  public String getPrompt() {
    return Prompt;
  }
  public String getDisputeReason() {
    return disputeReason;
  }

  public String getUtterance() {
    return Utterance;
  }

  public String getConfirm_Itent() {
    return Confirm_Itent;
  }

  public String getID_Number() {
    return ID_Number;
  }

  public String getSmart_In_Contact() {
    return Smart_In_Contact;
  }

  public String getCard_Number() {
    return Card_Number;
  }

  public String getPin_Number() {
    return Pin_Number;
  }

  public String getReverse_Stop() {
    return Reverse_Stop;
  }

  public String getMarchent() {
    return Marchent;
  }

  public String getMultiple() {
    return Multiple;
  }

  public String getSkip_terms() {
    return Skip_terms;
  }

  public String getConfirm_Terms() {
    return Confirm_Terms;
  }

  public String getSegment() {
    return segment;
  }
  public String getTest_CaSe_ID() {
    return Test_CaSe_ID;
  }
  public Debit_Order_Converted_Data(String Test_CaSe_ID,String Test_Case_Name,String Utterance,String Confirm_Devive,String ID_Number, String Intent,String DeepLnkUI_ID, String Prompt,String After_Intent,String Segment)
  {
    this.Test_CaSe_ID=Test_CaSe_ID;  this.Test_CaSe_ID=Test_CaSe_ID;
    this.Test_Case_Name=Test_Case_Name;
    this.Utterance=Utterance;

    this.ID_Number=ID_Number;
    this.Test_Case_Name=Test_Case_Name;
    this.Utterance=Utterance;
    this.Confirm_Devive=Confirm_Devive;
    this.ID_Number=ID_Number;
    this.Intent=Intent;
    this.DeepLnkUI_ID=DeepLnkUI_ID;
    this.Prompt=Prompt;
    this.AfterIntent=After_Intent;
    this.segment=Segment;
  }
  public String getConfirm_Devive() {
    return Confirm_Devive;
  }
  public String getAfterIntent() {
    return AfterIntent;
  }
  public String getDeepLnkUI_ID() {
    return DeepLnkUI_ID;
  }
  public String getIntent()
  {
    return Intent;
  }
}
