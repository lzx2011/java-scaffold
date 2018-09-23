package com.lzhenxing.javascaffold.javabase.collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lzhenxing.javascaffold.util.json.FastJsonUtil;
import org.junit.Test;

/**
 *
 * ClassName: SetPractice <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/5/8
 */
public class SetPractice {

    private static String allShids = "[10003718,10004212,10004281,10004370,10008080,10008100,10008199,10008266,10008343,10008464,10009111,10011142,10012415,10012546,10013849,10014167,10016207,10016323,10016903,10018626,10019071,10019930,10020016,10020855,10021434,10021642,10021738,10021756,10022105,10022276,10023926,10024604,10030133,10030771,10031434,10032038,10034352,10038656,10039570,10040045,10040203,10041128,10041289,10041339,10041545,10041609,10041634,10041732,10042039,10042437,10042457,10042700,10042712,10045058,10045120,10045143,10045206,10045286,10045538,10045971,10046346,10046386,10046403,10046682,10046683,10046768,10046844,10046857,10046893,10047003,10047112,10047253,10047274,10047544,10047555,10047717,10047738,10047797,10047958,10048021,10048220,10048260,10048336,10048435,10048477,10048524,10048705,10048789,10048927,10049130,10049134,10049262,10049308,10049357,10049386,10049410,10049437,10049487,10049504,10049612,10049621,10049752,10049869,10049891,10050099,10050310,10050372,10050481,10050529,10050627,10050667,10050743,10050781,10050867,10050958,10050975,10050991,10051022,10051025,10051036,10051206,10051345,10051653,10051817,10051959,10052164,10052304,10052682,10052920,10052987,10053066,10053081,10053260,10054261,10054392,10054471,10054578,10055220,10055861,10055950,10056029,10056290,10056298,10056501,10056649,10056721,10056941,10057881,10058932,10059330,10059623,10059686,10059730,10060617,10060709,10061353,10061542,10063027,10063149,10063230,10063611,10063779,10063953,10064263,10064334,10065910,10067397,10067697,10067974,10070189,10073866,10074541,10074552,10075701,10075722,10075829,10075940,10075982,10076459,10076867,10077052,10077127,10077148,10077172,10077473,10077497,10077566,10077570,10077766,10077768,10077769,10077861,10077868,10077874,10077875,10077876,10078029,10078030,10078031,10078032,10078034,10078109,10078136,10078166,10078185,10078198,10078200,10078741,10079003,10079004,10079061,10079065,10079262,10079263,10080767,10080825,10081100,10081128,10081167,10081176,10081650,10081662,10081878,10082000,10082161,10082432,10082476,10082565,10082572,10082573,10082700,10082754,10082842,10082864,10083009,10083012,10083017,10083030,10083063,10083133,10084080,10084210,10084246,10084375,10084389,10084392,10084556,10084561,10084650,10084679,10084747,10085242,10085682,10085693,10090180,10090501,10091053,10091226,10091255,10091420,10091507,10092121,10092228,10092239,10092326,10093479,10093687,10093749,10093752,10094032,10095856,10096609,10096622,10097540,10097602,10097689,10098512,10098515,10098543,10098547,10098548,10098695,10098905,10099243,10099919,10100022,10102443,10106547,10109576,10109577,10127910,10523053,10544636,10550078,10552189,10559045,10563444,10571521,10585765,10587038,10596577,10604491,10605895,10618437,10638609,10649347,10652419,10654745,10662896,10663480,10685204,10687150,10691563,10702694,10713896,10723300,10740473,10774518,10789367,10790026,10801435,10809455,10813050,10830955,10879895,10883519,10891640,10896069,10897070,10901369,10904830,10905911,10913447,10915090,10935578,10939321,10945626,10947211,10955203,10970537,10978524,12012177,12030652,12117317,12204056,12634234,12634555,12634584,12634996,12635027,12636288,12636546,12636714,12636889,12636890,12636891,12636892,12637321,12637562,12638512,12639746,12640683,12641921,12644408,12644724,12645736,12651331,12651533,12653745,12654419,50002001,50013094,50014031,50015067,50015080,50018180,50021001,50021011,50021013,50021014,50021016,50021018,50021023,50021028,50021035,50021044,50021052,50021055,50021059,50021061,50021065,50021068,50021070,50021073,50021074,50021077,50021080,50021081,50021082,50021083,50021084,50021088,50021099,50021100,50021102,50021104,50022009,50022011,50026236,50029169,50029513,50032356,50048027,50052137,50052567,50053996,50058305,50062008,50062639,50063255,50064579,50067597,50073140,50073216,50073226,50073273,50073277,50073279,50073281,50073402,50073421,50073479,50073634,50073858,50073864,50075156,50089433,50089720,50114063,50120139,50122647,50130545,50131117,50144020,50161610,50177411,50177945,50189848,50206403,50209245,50212039,50220353,50221065,50221576,50236344,50244190,50245663,50247149,50247378,50249396,50251732,50252225,50252264,50252827,50253370,50253438,50253585,50255077,50255094,50258255,50259323,50264005,50273160,50273243,50273357,50277020,50282412,50284066,50290006,50290375,50296716,50296855,50297950,50304160,50304184,50304415,50305305,50306125,50306126,50308411,50309336,50315041,50315630,50316507,50318585,50323213,50325642,50325973,50325974,50326050,50326107,50327725,50328548,50328600,50329014,50329754,50329906,50343055,50467799,50476013,50478683,50479593,50480873,50480940,50482524,50482967,50492650,50502011,50502905,50511037,50547061,50552450,50565135,50565197,50565198,50565199,50567453,50575576,50592022,50593028,50594032,50595031,50595039,50596036,50632022,50634013,50743033,50745034,50752006,50758004,50762006,50763003,50772011,50779028,50781175,50782182,50782194,50784189,50784217,50813052,50827096,50828074,50834030,50835023,50835025,50837022,50837024,50838011,50838017,50838021,50838025,50838026,50838035,50841003,50852025,50892026,50893027,50894036,50895026,50909016,50909093,50914095,50949919,50950217,50952894,50956005,50959001,50966012,50970026,50970028,51015607,51097012,51123204,51133041,51137302,51140134,51141218,51141562,51148253,51175015,51189882,51204428,51354495,51354511,51354535,51398320,51399004,51399324,51424060,51467150,51484299,51489035,51489122,51504333,51570770,51623053,51750002,51792023,51866072,51908056,51939012,52108001,52175035,52228318,52632001,52659002,52682004,52879004,52914033,52990029,53005013,53075008,53144061,53236176,53294015,53352115,53371188,53403063,53406075,53479198,53492012,53493007,53501056,53505009,53505039,53512019,53520155,53531195,53533200,53565038,53842008,53863263,53955008,53992036,54130055,54142018,54217064,54232050,10081411]";
    private static String importShids = "[10084561,50026236,50130545,50482967,12634584,10081167,51398320,10038656,50547061,10081176,10099919,10084556,50329906,10041545,10083133,50952894,10652419,10050099,50779028,10016207,50492650,10048220,50052567,10074552,10830955,10077875,10055861,10077876,10077868,10053081,10789367,10061353,10074541,10056941,10067974,10047253,10070189,51140134,50329014,10076867,10809455,52175035,10046386,50782194,10008464,10058932,10106547,10098695,10061542,50480940,10904830,10094032,51137302,52228318,10896069,10618437,10077766,12637321,10049134,50834030,10046346,10052920,10082161,10049130,10051959,10065910,10077768,10081100,50221065,10051206,50835025,50781175,50244190,10084375,10084246,10891640,50567453,10544636,10004212,50064579,51399324,10081128,10042437,51141218,10082089,50290375,12636288,10042457,10057881,10084389,50949919,10045206,10046403,50837024,50021011,50565199,50021013,10095856,50021014,10078185,10004281,10082000,50021023,10913447,10082842,10021756,10008080,10024604,10050310,50909093,10078200,10078199,50021001,10605895,10691563,10045143,10008100,50743033,50021055,51141562,50021052,10045120,50021028,10082864,50021035,10048435,10067697,10078166,50837022,50021077,52659002,50021078,10049410,50021073,10009233,50565135,50021084,10020855,50021080,10084747,50021083,50021082,50343055,10050372,50021061,10048477,50841003,10077172,10030771,50021059,50021068,10978524,50021065,10049437,12637562,10060617,10021642,10077127,51939012,10079065,54183017,50021104,10090501,54162018,51570770,54173019,10045058,10563444,10879895,10047003,10078109,50511037,50021088,50021102,10077148,50273160,10790026,10041732,50480873,50062639,10081878,10060709,10048260,50745034,10008199,10740473,53501056,10051653,10014167,10080825,10049386,10032038,10078031,10050481,10078030,51175015,10102443,50022009,10078032,12634234,10078034,10085693,10084080,10056029,10008266,50328600,10083009,12638512,10009111,10083017,10041634,10052682,10093752,12636546,10042712,10084650,10055950,50838035,50053996,10047112,50838026,50838025,51148253,10042700,10083063,50838017,10041609,10050529,50838021,10100022,12641921,50479593,10018626,10604491,53868209,10073866,50029513,12651533,50828074,10012415,50075156,50762006,50592022,10096609,10046844,10050627,10096622,10054471,10082573,10048705,10092326,50632022,10067397,50838011,10019071,10020016,51133041,50593028,10047738,50956005,51467150,53254060,10059330,50328548,10034352,10079262,10047717,10080767,12635027,10049612,10046768,12636714,10049621,10042039,50015067,12639746,12636889,12636891,12636890,10047544,12636892,12634996,51354535,51489035,10813050,51484299,10649347,10099243,10663480,10064263,10011142,51792023,50478683,10075940,50552450,50015080,10040045,10082700,10093479,10056298,10050781,10022105,50970026,10054578,50970028,10050743,51623053,10075982,10046857,50013094,10654745,50221576,10064334,54175027,10047555,10049752,10082754,51123204,10046893,51489122,50892026,10947211,10109577,10056649,53479198,10098547,10098548,12645736,50914095,10054261,10003718,10098543,50245663,10055220,50277020,50893027,10523053,10041128,52632001,10098515,10098512,10687150,50784189,10075829,10053260,50212039,10685204,10662896,10050867,50596036,10077570,50784217,10049891,50282412,50959001,12644724,10092121,50758004,53144061,51750002,50894036,10045538,10585765,10063953,50236344,50502905,50852025,10012546,10059623,10081411,10047958,50073281,52108001,50259323,50595031,10016323,10702694,10022276,50595039,10047797,10085242,10046683,50827096,10723300,10051025,10076459,51866072,53406075,50029169,10052164,50966012,10097540,10774518,10051022,10059730,10040203,10955203,10041289,10550078,10075722,51097012,51908056,10050991,50895026,53075008,50594032,10056501,10970537,10097602,10050958,50258255,10030133,10063149,10077497]";

    @Test
    public void test(){
        List<Long> allshidsList = FastJsonUtil.json2BeanList(allShids, Long.class);
        List<Long> importList = FastJsonUtil.json2BeanList(importShids, Long.class);
        //Set<Long> importSet = new HashSet<>(importList);
        //System.out.println(allshidsList.size());
        //Set<Long> allSet = new HashSet<>(allshidsList);
        //System.out.println(allSet.size());
        //System.out.println(importSet.size());
        //allshidsList.removeAll(importSet);
        //System.out.println(allshidsList.size());
        allshidsList.removeAll(importList);
        allshidsList.addAll(importList);
        System.out.println(allshidsList.size());
    }

}
