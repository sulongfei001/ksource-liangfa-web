(function(root, factory) {
	if (typeof define === 'function' && define.amd) {
		define([ 'exports', 'echarts' ], factory);
	} else if (typeof exports === 'object'
			&& typeof exports.nodeName !== 'string') {
		factory(exports, require('echarts'));
	} else {
		factory({}, root.echarts);
	}
}
		(
				this,
				function(exports, echarts) {
					var log = function(msg) {
						if (typeof console !== 'undefined') {
							console && console.error && console.error(msg);
						}
					};
					if (!echarts) {
						log('ECharts is not Loaded');
						return;
					}
					if (!echarts.registerMap) {
						log('ECharts Map is not loaded');
						return;
					}
					echarts
							.registerMap(
									'新乡市',
									{
										"type" : "FeatureCollection",
										"features" : [
												{
													"type" : "Feature",
													"id" : "410703",
													"properties" : {
														"name" : "卫滨区",
														"cp" : [ 113.866065,
																35.304905 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@BGAQ@W@M@IJ@^@@W@IN@BG@K@@@@@E@@@@@IBIBCA@]BKAMEMEMIEBOjMXMLMB{D@PKjQtH@FBHAFIHB@HFBN@DAJDDDHC@GBG`APFNAP@PCBA" ],
														"encodeOffsets" : [ [
																116607, 36159 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410702",
													"properties" : {
														"name" : "红旗区",
														"cp" : [ 113.878158,
																35.302684 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@ADAJ@J@@@@@F@@@@@LAHM@@J@X]@I@@J@N@XBRAHHCLCJ@@@@@@@T@P@PBJFPAJ@@@@@F@@ITB^AN@LDHJDHHFFH@BB@H@D@@@FBB@B@BBBB@@DD@@BDB@BBBBB@BBBBJDBB@@BBDBLDD@BBHBB@@BH@DBB@B@@A@MAMFQRY@MDaJKBOIWACCAA@MAKBIFONYLSFEABY@OGQ@@@@@@MYGQGKEGGAICCEAMEEIAIDCFEHMFM@MB@HAFIHIDUXAA" ],
														"encodeOffsets" : [ [
																116637, 36080 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410711",
													"properties" : {
														"name" : "牧野区",
														"cp" : [ 113.89716,
																35.312974 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@@AEGGECGGIKCM@]BSA@JE@@@@@I@OBIEOAO@S@@@@@@@I@KDGDABODO@MBOE_BAH@HGDCCICCBM@EA@GGAEJGBEAG@AFETFBNBPBLBBFJ@@KLDN@LBHFFDFB@NCHE@@N@HVCP@VA`@pBP@BB@@BB@@@@BB@BB@JHD@FBDBFDHDLBFBDBHDNHFFLLFFDFDB@GQQA@@ACAACAAACAC@C@AAACA@AAGAKRIPO@EAA@A@A@A@@CC@@AAAC@@@AAA@@BCBCD@@@@@@AB@BADADABAFAB@DA@@HAD@B@" ],
														"encodeOffsets" : [ [
																116720, 36172 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410704",
													"properties" : {
														"name" : "凤泉区",
														"cp" : [ 113.906712,
																35.379855 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@AFHTDLFJDHJDHDDHBFDBB@N@XEhIvI`CZCDA@L@NDFHHBJHBPDFFFXDLFDLDNDH@D@DEDADBFFDHHDDDCH@HJDF@HABEB@B@@AB@BADAD@F@B@F@PI@EAICCHQBK@A@@@CBABABAB@BAD@BADAHA@@HK@ACACEEEKKEEMGGCCAEAKAGCECCAEAC@IGA@@AAA@@@@AA@@AAO@oA_@UBO@UD@G@MF@DG@MEAECGEKAM@KC@LI@AEKAOAMAEAG`GPGBSLESGFKDQ@ID" ],
														"encodeOffsets" : [ [
																116464, 36208 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410721",
													"properties" : {
														"name" : "新乡县",
														"cp" : [ 113.806186,
																35.190021 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@JCR@LCHEFTTKHAHOH_FSBERsLi@O|CNANKNWPiFANJNFNFLB^AB@BBVWJCJGBE@GNAN@NEFGDEJCJBFFBNDFJDHBFHHLHRNZ@@@@@@HR@PAZFBTEZKPMJELANBB@HEFGDKBE@CAC@ECECCGIECECGECAECE@EAEAC@GAE@E@GAE@GAECCCAEFOFIGWKBKBKAKCQCEA@@@@MCSBO@QAYGGGGMISYUMGUAeAOAKK]oCWAAMA_FQFSFWDE@SBA@CBCDA@CB@@AAA@ADEDEBEDADBPLbAP@@@@@BCH@@@@CJI\\AXJ^HLBfDRALGTIHGHKTJRPNHPERKJEHABA@@BABA@A@ABCBA@ABCBA@ABABA@AD@B@BAB@@KPAJBDBNJHZHB@@BDBBDFV" ],
														"encodeOffsets" : [ [
																116464, 36208 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410724",
													"properties" : {
														"name" : "获嘉县",
														"cp" : [ 113.657249,
																35.261685 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@CA@AA@YGIGAMACBILO@@BA@A@ABCB@BABAB@DABAB@DABAB@B@BA@AB@BAFGLIFQGOOMIQLSHGJGHSBKCQAeGKI]BWJ[DI@@@@DG@A@@@@BOKaAOBCFCFAFCBCCM@A@E@C@AAAA@ECCAGCEAEAGACAAAAA@A@C@ADEDABA@@@@BAB@BA@@BABA@A@A@AA@CCCAC@CAEIICED@HBRCDC@MCIA@EDWCAK@GLBPAF@BA@I@E@GBE@@@@@@@C@CAA@@@@@GCICICAA@AAC@I@ABCDKBG@A@C@AAACCA@EAE@CA@J@F@DEFIHCFADABABEBABABAFADINGRAJ@@@@ADADEJGPEZGRELEJCBA@A@IAC@QGGAC@EBA@AD@D@BBF@B@BABABEBKBE@IBGFGFAB@@@@ABCDAHAH@F@DAFCFCFCPADELAH@P@N@HNRLDJHDDBF@FETGJ@B@@@@CHEHAB@@@@CDGFAJCJ@H@L@@@@ALJBH@RBT@J@PDTFL@L@HENAFBPHDD@@FD@@BDjZ\\LXHDBB@DBDADAF@B@H@D@F@D@B@D@BBB@DB@BbbJBNB\\AB@FB" ],
														"encodeOffsets" : [ [
																116460, 36195 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410725",
													"properties" : {
														"name" : "原阳县",
														"cp" : [ 113.965966,
																35.054001 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@BCJEHCFCHAHAFAD@HAJAD@D@FAF@F@F@H@DAFAF@FCHAFCF@DAFBFDFDFAFADAFCDCDAF@F@HBH@H@DAF@DAFAHEBCBCBCAC@EBGBCFCHAFAFAD@FAD@JAD@JAFAFAHCFAFCFAFAF@JAHAHBHDH@HDF@D@DCVABFHAF@HAFCFAHEDCFEDADCDCDCBEBEBG@CAIACCGACAAACAGCGACAECCCCACCCCCAE@CBCDCHCDCFADGECCAECCG@EAE@EBC@CBG@C@CAG@EBCBE@CCEAGEGACACAEAMAE@GBQ@CJ[@IBG@EDIFAEGCCCE@CE@GDOJIDEDEHKFMHINCJADKAOCMEOCO@GBMJKHMFEBEDEFGDIFEBQ@IAIAICKGMEGAKBE@KCI@E@EBG@EBGFEDEDEDEDGFKBICECEEEGqkIKGICI@CGGCA@@GEAA@@EAME_@SB@@A@A@OFIBC@KCGAmUQGK@EAiIE@YC]DUBIDA@ADIHCDIPABGLIJKJGDYFQ@_COGMCC@@@_E@@OCM@OHIPKHSNE@EBI@OAC@IB@@IBU@IBWDDP@J@H@H@DBF@DBFDFDHFFBFDFDDFFFFBDBBDBFDFBJDJBB@NFBDDD@B@@ABG@IDABEFAD@B@BBBF@NBH@BBBDBB@DAB@DAB@B@DDBF@FBB@DDBB@B@D@BAHCLAD@B@JBD@BBBJDJDHD@@@@B@DBD@@@@@@@F@HAF@J@B@@ABEAOHKL@DBCX@FJBNDD@DCAQ@GFCJDFJDBD@DBDDB@@B@B@BABAB@@ABA@AB@@@@ABCBCF@B@D@BBBBBDBHBFBFBHDDBFDB@BB@B@D@F@BDNB@BB@@DAB@DCDAB@TAF@XCTERE`ENBBBDX^pLLPBfBVBNHZVJTHNHHZHRBP@TAND@@@@FBRDLDLBLALA" ],
														"encodeOffsets" : [ [
																116735, 36024 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410726",
													"properties" : {
														"name" : "延津县",
														"cp" : [ 114.200982,
																35.149515 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@GBE@GBAEUBCDC@E@GCG@GCGAGBIBE@EBEBEDEBGDEBEBIBC@IBC@EBC@EBEBGBEDADAH@FBDADADADGFEBCBE@CBG@G@GAE@E@CBCDEDCBEBEBECECEACBE@EDGBEDE@EBCBG@E@E@E@EBC@C@IBGBC@EBGBGBEDGDIFADHXEJEPBFDDFDHBF@HBF@F@HBD@FBFBF@FDDBHFFDFDHJDDDF@FBD@DAFCLEHGFDBBDJXAPILCb@NQZERBN@N@BB@FAFAD@B@B@D@BA@@BAB@D@D@DBD@BBDB@@FDD@F@B@BABAB@DABADAB@B@B@FBDB@BJDDBH@B@FAB@FAB@B@@BB@DD@B@BBD@B@B@JBBHDDBFBDDDDFBHDD@HBD@H@DAFADAFCBCHEFEFABCDADCDAJCHADBHBD@FBHDHBFBFBFBHDDBFBHBFDHBDBJBFBH@F@JBD@JDFBFBDBDBFB@@@@BBFDDBFBDBHFFDDB@@@@BBBDFDFFDFDDFDFDB@NFNBLBJ@F@DBDB@DA@ABC@EDABA@ABA@BJBF@B@F@HAH@DCFCDF@F@fXPLF@D@@@DAB@HBbNB@@@@@@BDBBDPLLJJF@@@@DDBEJIHIPIBABABE@ABC@C@QAEEE@@@@AAKGOKEGCEACAA@CBABGBAHEDAB@BA@E@C@A@A@AB@B@@ABC@GDMBEDC@C@IAEEEEEEAACAEAG@C@CDCHCHCHEDEDCBACG@@@ABC@A@AAA@@AAAAA@AAGAA@G@@AA@AAACCAA@ABCBC@AAAA@ABAB@BA@AAA@A@@@CBAB@DAD@BAD@BADABA@ABA@A@E@AAA@@CCAEA@ACAAAACC@@A@A@ABCBA@C@CAC@@@ACAA@CBABA@@DADA@@DABABEBA@ADC@@@ABA@@@C@A@A@@CCA@CAA@A@AAA@AAA@CA@@ACCC@AAA@CBC@@@E@@BA@AB@@ABA@A@ABCA@@A@AB@L@B@D@BCB@BA@CAA@@CACA@CAAAAACCBEAA@CA@AAA@@@EBADABAA@DE@@@A@A@@BA@AAAAAAAAACC@CAC@CCG@ACAAAACACACDC@CBCHCB@BA@CAACCCAACCA@ACAACAAEAA@KBAB@D@@AB@DCDABABE@A@EAECECC@A@@@CD@B@DBD@B@BCD@@EBC@A@@@@@A@G@EBEAG@G@EBE@GAC@GAGEEEACCCACACAACEEGCCCECCAECCAECEAEACACACCCECCCAACCGCEAGCEACAGA" ],
														"encodeOffsets" : [ [
																116985, 35960 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410727",
													"properties" : {
														"name" : "封丘县",
														"cp" : [ 114.423405,
																35.04057 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@@A@A@G@@@A@A@ABC@A@ABGBC@@@A@A@ABA@E@@@ABA@A@@@C@ABA@A@A@A@@BCB@BA@@F@DBD@B@D@FB@@DBB@DBB@FBDBD@@BB@BBD@@@BBF@F@F@B@D@B@DAHAD@DCFADA@AB@BA@@DEDCB@BCB@BADCBCBADAFEDC@AFE@@BADCDAB@BAB@B@BAD@B@BAF@BAB@D@B@LCHAB@L@BAFCB@B@BAFADAB@DABAD@B@DCBABADCDCBA@ABABC@ABCBC@AB@BCDABABC@ABE@A@CB@@A@A@ABC@ABABA@@BABCBABA@ABABA@@BCBCBABABCBA@@DCFCBADCFCFCHEBA@@@@BCDADCDCHCFEJGFCDGFCDCDCDEDEDADCDADCBC@@F@B@HAF@B@@AB@BAB@@AB@BABAB@@ABAB@B@BABABA@CAG@AH@BAFAFAB@FAB@@A@ALCF@DAGGOKCCCA@@EAGAE@CCEEECCCEECGCG@IDEFEDAFEFABADADCBADCDADCHEJEDCDCBEBCBC@GAECECEEEECCCECECGAEAG@EAG@E@I@C@AAGCI@MHOFOBKBGHGFGFCFEDC@CAAEECMEGAOBMDG@EBIDGBMBIDO@KBODKBI@IAOCKCMCQEWGWCSAKAQFSFUFWFQFIBODODOHMLUNSJMHM@K@IEIIGKMMSMOISA_Ba@oBYAWAUCE@@DDFDDFHEBCJ@FAH@JI\\@DAR@HBFBNBFBDBDFHBHDF@DAFAD@FBH@D@DAH@DAD@FBF@FDHFDDBFDCHEBCDGDCDAD@DBFDDDDBDDDDDBFBDDHBHBDBBBDDHBDBJ@DAHAFAFCDCDCDCBEFCDGFEBEDHBDBFBHDFBHDDDBBDDFDDDBDBDBDBFDFBFDDBFDDDFDDFHDFBBBDBDDDBDFFHFHBD@HBF@FAH@H@FBFAH@B@@@@@B@D@FA@@DC@A@AAC@C@ADC@@B@D@FDFDFBB@F@BABADC@CBA@@@CBALAB@FBBBBDDB@BDBBDDBDDBB@DABA@GDAD@DCDBDBDBDBBDB@BDH@DBD@DDDBBBBBBBB@BAB@@@B@B@@CFB@@@@BB@BD@B@@FDB@BBF@FBD@HBD@D@B@@ABABA@AB@BAB@B@D@B@@AH@B@BC@A@ABA@@@C@AAAAC@C@A@@BCDAB@BAB@D@BBD@ABBBBBBBDFDBBBB@BBB@D@LDH@BAD@FAD@BAB@FADABABADABAD@BA@AB@B@" ],
														"encodeOffsets" : [ [
																117237, 36052 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410782",
													"properties" : {
														"name" : "辉县市",
														"cp" : [ 113.802518,
																35.461318 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@A@AFGBE@IC@GDGCCGCCGEECACBCFC@G@MCKCECCKEWEEOCGAAIGGCE@M@KCBYD_DuJgJWFM@A@CAAECGGCICCGEICKGSBEEUACEAA@[BMAIAaa@ACAA@AAC@A@C@E@C@G@A@E@CBCBCAA@CAWG[KiYAC@@EC@@CCOGEAMBGFK@K@SEOCI@S@QAG@IAE@A@CDAHA@A@O@MAE@CAC@CB@BAX@HAL@DABGDAFAD@DBDBN@FED@@@@EBOPHFCHCD@JFBNAFDFHALA@@BIJCFEBCDGJADAF@NCJCJKTIRGHSPMJQHCDCBIHCBGBEFCDGDCDAD@JAJ@HCFCFGFBFFLBBBBFDB@DAD@BBD@B@B@B@B@FFJFDBDD@@B@BADABC@@B@B@B@DBD@@BB@@@BABABA@AB@BABA@@DAF@DBHBFBBBBBB@B@D@@@@@BABBBBB@B@BBDB@@J@FBDBD@@@DBB@TFLIF@X@DH@FSR@BDDFLFPCH@LDPNPTPNLFNF@D@DBDBBB@@@B@BAB@B@D@BBBB@@BBBBBBH@@BH@B@BB@DBD@F@BBB@BD@@@BAB@@@BAB@BADA@@B@B@F@B@DAD@BA@@DABA@@DABAB@DAB@B@@@B@BBB@B@BAB@BBB@@B@BBB@BBDBDBB@D@@AB@@A@ABA@C@@@ABA@A@@@ABA@ABAAA@AB@@ABAB@D@B@BBB@DBBA@@@I@ABA@A@@BA@A@@DC@@BAD@B@B@DAB@H@B@BAB@H@F@J@B@B@B@BBD@D@B@DAB@DAD@HBBB@@BDAD@B@D@B@B@L@D@H@@@B@B@@AD@B@@CBAB@BAB@@@D@DADDDFDAFORULUFA@@B@@@D@BB@DDDB@@DBBDBBBH@@AJINITGRANLVHJFV@JAFA@ABABADAF@B@D@B@BA@A@AA@@ECC@A@A@AB@BA@@B@D@@@BBBBBB@DBB@BBB@B@DBB@F@D@F@F@B@@FA@ABEBA@@@CBABCDA@C@A@@AC@A@A@@B@FFHADGBCD@HFHF@@@BAB@NAB@BAB@FBF@B@DBB@@BB@B@BBBBHDLDLAJKNCRHJHDLX@T@V@D@@@F@JBD@FAD@BABAB@BA@A@@ACAAAA@AAABA@A@@BA@A@ABA@ADABAB@@BB@@@F@BBB@D@BB@@DDB@B@BBBADABA@A@@BAB@B@F@B@@@DBD@B@BAB@BCBCACBA@A@@HIJQFIHMJK\\_NQLE@@@@HEJU@YAYO]ESFSVI^CZBhHPBJDNF@S@Q@@@@@CJORMVOJIBYAKB[BUDGJKFCVKPGLGNMF]@UC[@A@@ACBGDQBY@A@ACCAC@AAAAC@A@@@A@AAE@A@AAA@@@CAA@CAA@A@A@A@EAA@CAAAAAA@AAACI@AAA" ],
														"encodeOffsets" : [ [
																116664, 36299 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410781",
													"properties" : {
														"name" : "卫辉市",
														"cp" : [ 114.065855,
																35.404295 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@@BGL@@GBCBABC@ABA@ABABAB@D@@@BALGRDDBJ@FOJE@A@E@C@CBABA@@BA@BB@BDJBB@BBBBBBB@DBB@F@B@B@BBB@DBB@D@@BB@B@BBF@B@B@@@BBDBB@BBDDD@B@BAZCRAHBD@@@BD\\@VE^MNKHOHULEDILCHAVA\\BLAZIJUPQNIP@D@@@@@R@TJBX@HAPIDAD@BBLFD@T@FBDBDFDLAB@DDLDFBBD@JALCFAHGFEBCBAB@F@FAD@DABBHDHFDDLHLDJDD@B@DCTGDAFAJADCBA@G@C@ABABAD@HAJ@HCLCHEBABABCJWDE@C@CBCBGDMBEBCAC@CCCGG@A@A@EDI@C@EAIEQ@C@CDEFEXQFGBEBS@CBADAHCJCNAV@JAH@JCJGRKJEPGNGHCNAHCBAB@BAB@D@D@H@D@DAHAD@D@D@DDB@FBF@D@FADCFCDCBAD@B@F@HAXGPEHAXENCHADAF@F@F@J@F@AIAACCCAICEAAA@EBC@C@ACCAEAE@EBCFEJEHCH@J@DAFAFELIJGLCL@DCDE@CBG@G@E@AAEAIB@BAB@BAFCD@BAB@@CCACAE@I@KAMAMEA@ECECCCCEEEECACAA@@@@CAECGECAEACAECAA@@@@EACACAEAEAICC@IAE@G@EAIACAGAECGAEACAGCEAEAEAGAGCEAC@GACAGBIDCBCDCBADEBEFGFADEDCBEBCBG@C@GAC@GCEACCCCEACAGCAA@I@A@AAC@A@ACCA@@AA@A@EBA@EBA@G@CAIC@ACAEAA@A@A@CBABCBA@ABABA@E@C@EC@@CAAAC@CAC@C@A@AB@@ABC@A@A@C@EBEBA@A@A@CAG@@AA@GAAAC@KCCAAA@@AAICAAAAA@AAAAA@AC@@CC@@AAAAA@A@EA@@C@G@A@A@C@GB@@CBA@EBABCBCBABA@@B@@@@C@ADAD@@BB@B@@BDBB@@DD@@@B@B@BBB@FOPQJBLBH@BDBBB@B@DBDBDBBBDDB@BB@RR@H" ],
														"encodeOffsets" : [ [
																116706, 36249 ] ]
													}
												},
												{
													"type" : "Feature",
													"id" : "410728",
													"properties" : {
														"name" : "长垣县",
														"cp" : [ 114.673807,
																35.19615 ],
														"childNum" : 1
													},
													"geometry" : {
														"type" : "Polygon",
														"coordinates" : [ "@@EgA@A@A@C@A@C@CA@@AAA@AAAAA@@ACAAA@ACCA@AAAAEEAA@@AA@ACA@ACCA@AAAA@AAA@AAAEGA@@AAAA@@CCAAAAAAA@@EGAAACAA@AAAAC@CAA@AAEAAAA@CAA]EMCEC@@AGBeDw@AAAAACAA@M@U@eEMAOCMEOGKGKKIMGKAGBE@CLG\\SXUFGDEFEFK@A@M@GACKK_QaGQCGECEGKCICGEG@ABABABCDADALCFADAF@B@N@H@B@@@BABCBA@A@C@CACACCCGGIEGCICECGACAIFA@EAQGCAIEECMIGIIGCBE@KD@B@BA@EBA@EBEBABG@@BBH@DABABABA@A@AB@BA@ABABA@@BA@ABA@@BA@E@GBA@E@@@ADCDCBCDCBCFCFCDCDEDCHEDIHEFGDCDCDCBAD@@@@ABGFEDEDCDABEDCD@@ABADABABADAD@@ABAB@BABABADAB@@ABAB@BAD@B@B@BA@@D@BAF@BADABCBADA@@BADAD@BADAB@BABCDCDABABCDA@C@ABCBA@CBEBABA@A@EDABK@A@GBKDA@C@A@ABE@ABA@C@ABA@A@ABA@CBCDAB@@EF@BCDEFCBABADCDABA@ADA@CDCF@@ABA@@BCBEBCDC@GBCBA@C@A@E@E@E@AA@@C@AAA@@AC@CAEAA@CAA@CA@@EAC@A@C@CAE@@@ABA@AD@@@B@B@BAB@B@D@@@BAB@B@@@FAB@B@B@B@@ADAH@B@BAD@B@B@B@@@H@B@BBB@B@@@BBDBF@B@BBBDFBB@@BBB@BB@@BBBBDB@@BB@BB@DBBBD@DBDDFBB@B@D@B@B@BBD@DBB@BBB@LFFBFDDD@BBD@@BB@BBB@B@DBDBB@D@@@DBDB@BDBB@BDBDDBD@@BBFFBB@@DBFBB@FBF@DBB@FBD@DBFBB@BBB@PD@BBBB@B@BBBBB@BBDBB@BBFBDBB@B@F@HDDBB@DBB@D@B@D@BAF@F@H@BAB@FAB@DAD@BA@@BAH@FAD@D@B@D@F@@@R@B@DBFBDBBB@@BBDDDDB@BFDD@@@BBB@F@B@B@HBB@B@DB@@DBD@B@B@B@D@BADA@ABAB@@@BAB@BBB@BB@@BBBD@H@DBH@BBB@DBD@@@B@N@@BB@D@B@D@B@J@F@HAB@FAB@FAF@B@BBFDDB@@H@D@B@B@B@DAB@DAB@D@F@F@D@D@D@DBB@B@F@D@D@DBF@@@DBDB@@BD@@@B@@@B@D@@@B@B@BB@@BDBB@BBB@@@BBBBFDDBBB@@BBB@BBJDDDHDBB@@BBBBBBFFBDDBFDBBFBB@DDB@H@HAF@H@F@H@FBPDPBJALCNAVCN@NA@@@@@@JANEJKHIDEDMDGDA@@@@@AHAJAHA@@@@@@[" ],
														"encodeOffsets" : [ [
																117778, 36186 ] ]
													}
												} ],
										"UTF8Encoding" : true
									});
				}));