package com.example.fodashboard.common;

/**
 * Created by 10037 on 6/23/2017.
 */

public class AppConstants {
    public static final int build_method = 2;



    public static  String OTP_TOKEN;
    public static  String FINGER_TOKEN;
    public static  String OPS_DEMO_SERVER;
    public static  String SARAN_SERVER;

  //  public static  String IP;

    static
    {
        switch (build_method){
            case 1: //LIVE
                OPS_DEMO_SERVER = "https://dev-api-collection.newstreettech.com/api/";


                break;
            case 2: //UAT
                OPS_DEMO_SERVER = "https://dev-api-collection.newstreettech.com/api/";

                break;

        }
    }



    public static final String STRUCTURE_ID_GROUP_NAME = "groupname";
    public static final String STRUCTURE_ID_ALL_GROUP_LIST = "latestGroups";
    public static final String STRUCTURE_ID_LOAN_BY_PROSPECT_GROUP_LIST = "loanByProspect";
    public static final String STRUCTURE_ID_ALL_PROSPECT_LIST = "latestProspects";
    public static final String LOCALE_ID_KEY = "localeID";




    public static final String REQUEST_PORTAL = "getDashboardDetails";
    public static final String DISBURSMENT_TODAY = "disbursementCount";
    public static final String DISBURSMENT_MONTH = "currentDisbursementCount";
    public static final String PERFOMENCE_REPORT = "performanceReport";
    public static final String PENDING_STATUS = "customerCountByStatus";
    public static final String PENDING_STATUS_CUSTOMER = "customerDetailsByStatus";
    public static final String CONVERSION_CHART = "conversionChat";








    //-----------------------------------DEMO OPS REQUIREMENT---------------------------------------
    /*public static String RECEIVER_OPERATOR = "shecomoperator";
    //public static final String OPS_DEMO_SERVER = "https://mifix-demo.krypc.com:24001/microFinance/";
    public static final String OPS_DEMO_SERVER = "https://mifix-demo.newstreettech.com:24001/microFinance/";//24-Aug-2021
    //public static final String OPS_DEMO_SERVER = "https://nstshecom.krypc.com:21001/microFinance/";
    //public static final String OPS_DEMO_SERVER = "https://nstmigrationdata.krypc.com:21001/microFinance/";
    public static String SUBMIT_STRUCTURE = OPS_DEMO_SERVER + SEND_PORTAL;//Submitting data
    public static String FETCH_STRUCTURE = OPS_DEMO_SERVER + REQUEST_PORTAL;//Fetching data
    public static String LOGIN = OPS_DEMO_SERVER + LOGIN_PORTAL;
    public static String FETCH_HISTORY_STRUCTURE = OPS_DEMO_SERVER + REQUEST_HISTORY_PORTAL;
    public static String CHANGE_PASSWORD = OPS_DEMO_SERVER + REQUEST_CHNAGE_PASSWORD;//Submitting data
    public static String CIBIL_SCORE = OPS_DEMO_SERVER + REQUEST_CIBIL_SCORE;//Submitting data
    public static String EKYC_NAME_CHECK = OPS_DEMO_SERVER + REQUEST_EKYC;//EKYC name check
    public static String IPFS_IMAGE = OPS_DEMO_SERVER + FETCH_IPFSIMAGE;//Submitting data
    public static String DEDUPE_API = OPS_DEMO_SERVER + DEDUPE;//Submitting
    public static String HIGHMARK_API = OPS_DEMO_SERVER + REQUEST_HIGHMARK_SCORE;//Submitting*/


    //-----------------------------------LOCAL SERVER(SARAN)----------------------------------------
    /*public static String RECEIVER_OPERATOR = "nextruoperator";
    public static final String SARAN_SERVER = "http://192.168.1.86:22001/microFinance/";
    //public static final String SARAN_SERVER = "http://192.168.1.214:22001/microFinance/";

    public static String SUBMIT_STRUCTURE = SARAN_SERVER + SEND_PORTAL;//Submitting data
    public static String FETCH_STRUCTURE = SARAN_SERVER + REQUEST_PORTAL;//Fetching data
    public static String LOGIN = SARAN_SERVER + LOGIN_PORTAL;
    public static String FETCH_HISTORY_STRUCTURE = SARAN_SERVER + REQUEST_HISTORY_PORTAL;
    public static String CHANGE_PASSWORD = SARAN_SERVER + REQUEST_CHNAGE_PASSWORD;//Submitting data
    public static String CIBIL_SCORE = SARAN_SERVER + REQUEST_CIBIL_SCORE;//Submitting data
    public static String EKYC_NAME_CHECK = SARAN_SERVER + REQUEST_EKYC;//EKYC name check
    public static String IPFS_IMAGE = SARAN_SERVER + FETCH_IPFSIMAGE;//Submitting data*/


    //-----------------------------------LOCAL SERVER(Chirsty)--------------------------------------
  /*  public static String RECEIVER_OPERATOR = "nstdpoperator1"; //for NST
   // public static String RECEIVER_OPERATOR = "nextruoperator";//for nextru
    //public static final String SARAN_SERVER = "http://192.168.1.123:22001/microFinance/";//nextruprospectblacklist
   // public static final String SARAN_SERVER = "http://192.168.1.123:24001/microFinance/";//NST
    public static String SUBMIT_STRUCTURE = SARAN_SERVER + SEND_PORTAL;//Submitting data
    public static String FETCH_STRUCTURE = SARAN_SERVER + REQUEST_PORTAL;//Fetching data
    public static String LOGIN = SARAN_SERVER + LOGIN_PORTAL;
    public static String FETCH_HISTORY_STRUCTURE = SARAN_SERVER + REQUEST_HISTORY_PORTAL;
    public static String CHANGE_PASSWORD = SARAN_SERVER + REQUEST_CHNAGE_PASSWORD;//Submitting data
    public static String CIBIL_SCORE = SARAN_SERVER + REQUEST_CIBIL_SCORE;//Submitting data
    public static String EKYC_NAME_CHECK = SARAN_SERVER + REQUEST_EKYC;//EKYC name check

    public static String IPFS_IMAGE = SARAN_SERVER + FETCH_IPFSIMAGE;//Submitting data
    public static String DEDUPE_API = SARAN_SERVER + DEDUPE;//Submitting
    public static String HIGHMARK_API = SARAN_SERVER + REQUEST_HIGHMARK_SCORE;
*/






    //----------------------------------SHECOM SERVER---------------------------------------------
    //CHECK DP OPERATOR
    public static String RECEIVER_OPERATOR = "shecomoperator";
    //public static final String OPS_DEMO_SERVER = "https://shecom.krypc.com:21001/microFinance/";

  //  public static String SUBMIT_STRUCTURE = OPS_DEMO_SERVER + SEND_PORTAL;//Submitting data
    public static String FETCH_STRUCTURE = OPS_DEMO_SERVER + REQUEST_PORTAL;//Fetching data
  //  public static String LOGIN = OPS_DEMO_SERVER + LOGIN_PORTAL;









}

