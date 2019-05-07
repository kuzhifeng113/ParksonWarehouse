package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class MallHomeFourBean implements Serializable{

    /**
     * categoryList : [{"image":"http://image.bscvip.com/category_images/1557124873204656.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554283956738870.jpg","name":"酒水饮料","categoryId":41}],"name":"热门","categoryId":1},{"image":"http://image.bscvip.com/category_images/1557124983568629.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554970162285902.jpg","name":"厨房用品","categoryId":49},{"image":"http://image.bscvip.com/category_images/1554970276268001.jpg","name":"清洁用品","categoryId":16},{"image":"http://image.bscvip.com/category_images/1556096681060321.jpg","name":"家居用品","categoryId":50},{"image":"http://image.bscvip.com/category_images/1554283570353091.jpg","name":"收纳用品","categoryId":20},{"image":"http://image.bscvip.com/category_images/1554283304664387.jpg","name":"纸品湿巾","categoryId":15},{"image":"http://image.bscvip.com/category_images/1554283603655421.jpg","name":"家用电器","categoryId":21}],"name":"生活日用","categoryId":11},{"image":"http://image.bscvip.com/category_images/1557124896022836.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554283759442377.jpg","name":"沐浴清洁","categoryId":28},{"image":"http://image.bscvip.com/category_images/1554969690483787.jpg","name":"女性护理","categoryId":45},{"image":"http://image.bscvip.com/category_images/1554283805578672.jpg","name":"口腔护理","categoryId":31},{"image":"http://image.bscvip.com/category_images/1554283776657397.jpg","name":"身体护理","categoryId":29},{"image":"http://image.bscvip.com/category_images/1554283742575202.jpg","name":"洗发护发","categoryId":27},{"image":"http://image.bscvip.com/category_images/1554283817373895.jpg","name":"美容彩妆","categoryId":32},{"image":"http://image.bscvip.com/category_images/1554283791587374.jpg","name":"面部护理","categoryId":30}],"name":"个护美妆","categoryId":3},{"image":"http://image.bscvip.com/category_images/1557126239676618.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554283927871680.jpg","name":"摄影摄像","categoryId":39},{"image":"http://image.bscvip.com/category_images/1554283940806407.jpg","name":"影音娱乐","categoryId":40},{"image":"http://image.bscvip.com/category_images/1554283916488135.jpg","name":"手机配件","categoryId":38}],"name":"3C数码","categoryId":4},{"image":"http://image.bscvip.com/category_images/1557124958769423.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554283882305905.jpg","name":"服装内衣","categoryId":36},{"image":"http://image.bscvip.com/category_images/1554969721148575.jpg","name":"箱包","categoryId":46},{"image":"http://image.bscvip.com/category_images/1554286563316374.jpg","name":"珠宝配饰","categoryId":44},{"image":"http://image.bscvip.com/category_images/1556097683786438.jpg","name":"鞋帽袜","categoryId":52}],"name":"服装配饰","categoryId":5},{"image":"http://image.bscvip.com/category_images/1557124889367190.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554969580248212.jpg","name":"尿裤湿巾","categoryId":26},{"image":"http://image.bscvip.com/category_images/1554969625133715.jpg","name":"洗护用品","categoryId":24},{"image":"http://image.bscvip.com/category_images/1554969653410867.jpg","name":"儿童玩具","categoryId":22},{"image":"http://image.bscvip.com/category_images/1554969602700436.jpg","name":"喂养用品","categoryId":25},{"image":"http://image.bscvip.com/category_images/1554969638227757.jpg","name":"儿童家居","categoryId":23}],"name":"母婴用品","categoryId":2},{"image":"http://image.bscvip.com/category_images/1557124972955401.png","categoryList":[{"image":"http://image.bscvip.com/category_images/1554969758910541.jpg","name":"酒水饮料","categoryId":47},{"image":"http://image.bscvip.com/category_images/1554283969165849.jpg","name":"休闲零食","categoryId":42},{"image":"http://image.bscvip.com/category_images/1554969917940013.jpg","name":"冲调茗茶","categoryId":48}],"name":"休闲食品","categoryId":7}]
     * advBannerList : [{"sec":0,"goodsId":419,"place":2,"rushId":16,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=419&share=bc3707391851024f","type":6,"url":"http://image.bscvip.com/banner_images/1557111833008037.png"},{"sec":0,"goodsId":414,"place":2,"rushId":16,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=352&share=bc3707391851024f","type":6,"url":"http://image.bscvip.com/banner_images/1557111994295518.png"},{"sec":0,"goodsId":65,"place":2,"rushId":0,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=65&share=bc3707391851024f","type":3,"url":"http://image.bscvip.com/banner_images/1556270773283099.jpg"},{"sec":0,"goodsId":15,"place":2,"rushId":0,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=15&share=bc3707391851024f","type":3,"url":"http://image.bscvip.com/banner_images/1556271490902330.jpg"},{"sec":5,"appVer":"","goodsId":321,"place":2,"rushId":0,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=321&share=bc3707391851024f","type":3,"url":"http://image.bscvip.com/banner_images/1556244247724666.png"},{"sec":0,"goodsId":49,"place":2,"rushId":0,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=49&share=bc3707391851024f","type":3,"url":"http://image.bscvip.com/banner_images/1556504356713574.jpg"},{"sec":3,"appVer":"1.0.1","goodsId":160,"place":2,"rushId":0,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=160&share=bc3707391851024f","type":3,"url":"http://image.bscvip.com/banner_images/1556244851541022.jpg"},{"sec":0,"goodsId":285,"place":2,"rushId":0,"tagUrl":"https://api.bscvip.com/detailv2/detailshare.html?goodsId=285&share=bc3707391851024f","type":3,"url":"http://image.bscvip.com/banner_images/1555640886878554.png"}]
     * goodsCategoryList : [{"titleImage":"http://image.bscvip.com/category_images/1557126036340836.png","image":"http://image.bscvip.com/category_images/1557124873204656.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1550483079035433.png","compareUrl":"","goodsId":289,"transport":0,"title":"脑黄金含量≈2倍深海鱼油的牡丹油","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1550483097320712.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097449016.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097592525.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097723281.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097886318.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098026257.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098190640.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098330264.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098467249.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098616903.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098720319.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098886427.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098988402.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099123402.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099253331.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099393827.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099581674.png\" />","favoriteNum":8,"price":278,"supplier":"","name":"牡之元 牡丹籽油","vipPrice":91.8,"wantNum":0,"sortBy":4,"categoryId":41,"sellNum":773,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1550486241426735.png","compareUrl":"","goodsId":290,"transport":0,"title":"来自汉中的牡丹籽油","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1551765029263130.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029425547.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029607348.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029766617.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029979041.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765030541138.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765030783889.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765031194836.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765031340299.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765031898580.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765032082445.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765032471455.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765032986883.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765033197857.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765033398797.png\" />","favoriteNum":1,"price":628,"supplier":"","name":"牡之元 牡丹籽油","vipPrice":218,"wantNum":78,"sortBy":0,"categoryId":41,"sellNum":99,"freeShopping":80}],"name":"热门","categoryId":1},{"titleImage":"http://image.bscvip.com/category_images/1557126275999114.png","image":"http://image.bscvip.com/category_images/1557124983568629.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1553766387689606.jpg","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b.w4011-5530324556.37.38696f02NfnhDv&id=540919185673&rn=1e6bd036b6f13ff6e3df48115ee8cbc0&abbucket=2","goodsId":302,"transport":0,"title":"全身水洗  冲插两用 智能防夹须","cartNum":0,"content":"<img src='http://image.bscvip.com/content_images/1553096400685319.jpg' /><img src='http://image.bscvip.com/content_images/1553096385554231.jpg' /><img src='http://image.bscvip.com/content_images/1553096368702453.jpg' /><img src='http://image.bscvip.com/content_images/1553096355492744.jpg' /><img src='http://image.bscvip.com/content_images/1553096341424700.jpg' /><img src='http://image.bscvip.com/content_images/1553096322956130.jpg' /><img src='http://image.bscvip.com/content_images/1553096308956570.jpg' /><img src='http://image.bscvip.com/content_images/1553096295177377.jpg' /><img src='http://image.bscvip.com/content_images/1553096285544342.jpg' /><img src='http://image.bscvip.com/content_images/1553096271221734.jpg' /><img src='http://image.bscvip.com/content_images/1553096260764844.jpg' /><img src='http://image.bscvip.com/content_images/1553096246241966.jpg' /><img src='http://image.bscvip.com/content_images/1553096231324569.jpg' /><img src='http://image.bscvip.com/content_images/1553096217006993.jpg' />","favoriteNum":0,"price":339,"supplier":" 全国联保 中国剃须刀行业公认第一品牌","name":"飞科 电动全身水洗飞科剃须刀男充电式刮胡刀","vipPrice":168,"wantNum":0,"sortBy":599,"categoryId":21,"sellNum":83,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548731786289448.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-18363830149.89.192b582cxhlplj&id=569077139849&rn=a3d2dd8e19d5bd85609ed678a59b9ea7&abbucket=13","goodsId":264,"transport":0,"title":"倒油时 盖子会自动打开","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547869988795905.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869988974028.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869989204889.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869989430296.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869989570551.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869989760168.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869989970422.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869990164275.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869990373442.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869990573774.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869990781156.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869990974160.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869991096223.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869991232732.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869991372901.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869991569894.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869991758715.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869992038058.png\" /><img src=\"http://image.bscvip.com/goods_images/1547869992342678.png\" />","favoriteNum":1,"price":22.8,"supplier":"抖音爆款","name":"乐博乐博 自动开合玻璃油壶 ","vipPrice":13.9,"wantNum":0,"sortBy":500,"categoryId":49,"sellNum":128,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1555580258150832.png","compareUrl":"","goodsId":84,"transport":0,"title":"家里来客人了 碗够用吗？","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1546147241508754.png\" /><img src=\"http://image.bscvip.com/goods_images/1546147246520904.png\" /><img src=\"http://image.bscvip.com/goods_images/1546147252702206.png\" /><img src=\"http://image.bscvip.com/goods_images/1546147257947657.png\" /><img src=\"http://image.bscvip.com/goods_images/1546147263387882.png\" /><img src=\"http://image.bscvip.com/goods_images/1546147269161045.png\" /><img src=\"http://image.bscvip.com/goods_images/1546147275210703.png\" />","favoriteNum":0,"price":69.9,"supplier":"全新食品级PP材质","name":"依美家 加厚PP胶碗 (340ml)  20个装*2件","vipPrice":15.9,"wantNum":0,"sortBy":500,"categoryId":49,"sellNum":301,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553097017158096.jpg","compareUrl":"https://chaoshi.detail.tmall.com/item.htm?spm=a220o.7406545.0.da321h.4aa646a9kZ8sKt&id=41242797369","goodsId":304,"transport":0,"title":"1200w 冷热风 过热保护 可折叠 ","cartNum":0,"content":"<img src='http://image.bscvip.com/content_images/1553097402754909.jpg' /><img src='http://image.bscvip.com/content_images/1553097392191309.jpg' /><img src='http://image.bscvip.com/content_images/1553097378762268.jpg' /><img src='http://image.bscvip.com/content_images/1553097365099823.jpg' /><img src='http://image.bscvip.com/content_images/1553097348241860.jpg' /><img src='http://image.bscvip.com/content_images/1553097336742743.jpg' /><img src='http://image.bscvip.com/content_images/1553097326935576.jpg' /><img src='http://image.bscvip.com/content_images/1553097317759724.jpg' /><img src='http://image.bscvip.com/content_images/1553097306880895.jpg' /><img src='http://image.bscvip.com/content_images/1553097198833307.jpg' /><img src='http://image.bscvip.com/content_images/1553097108457293.jpg' />","favoriteNum":0,"price":65,"supplier":"\"原装正品 全国联保\"","name":"飞科 电吹风机家用学生迷你便携式小功率宿可折叠吹风筒","vipPrice":45.8,"wantNum":0,"sortBy":499,"categoryId":21,"sellNum":42,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1556516528747061.jpg","compareUrl":"https://item.jd.com/25547278626.html","goodsId":293,"transport":0,"title":"为宝宝用纸安全定制 不漂白 食品级","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1552369932157322.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369932281007.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369932480807.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369932828565.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369932952351.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369933153204.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369933344707.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369933579916.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369933878599.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369934376419.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369934783734.png\" /><img src=\"http://image.bscvip.com/goods_images/1552369935294622.png\" />","favoriteNum":0,"price":64.9,"supplier":"成都大熊猫繁育研究基地唯一指定用纸","name":"慕纯（mooture）抽纸竹纤维婴儿纸抽本色纸巾卫生纸原竹浆原浆纸 18包 390张/包","vipPrice":57.9,"wantNum":0,"sortBy":497,"categoryId":15,"sellNum":395,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553132058749233.jpg","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.ab08f7486RJsiR&id=564946631104&skuId=3573037325412&areaId=440100&user_id=3003492692&cat_id=2&is_b=1&rn=57e73f14ee97f90b3782ffdc276a1bef","goodsId":295,"transport":0,"title":"可刮平 一键防潮 防滑可提 食品级无异味","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553091737222072.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091723298701.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091710255678.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091699978736.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091685040210.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091673816083.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091659848899.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091643911391.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091629976302.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091611733202.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091599319594.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091587615937.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091574218649.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091558907331.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091538326129.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091496052073.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091473815410.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553091445722484.jpg\" />","favoriteNum":0,"price":59,"supplier":"","name":"安扣ANKOU 奶粉罐奶粉盒米粉罐密封罐","vipPrice":49.9,"wantNum":0,"sortBy":496,"categoryId":49,"sellNum":55,"freeShopping":80}],"name":"生活日用","categoryId":11},{"titleImage":"http://image.bscvip.com/category_images/1557126217906099.png","image":"http://image.bscvip.com/category_images/1557124896022836.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1555925115142736.jpg","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.15-b-s.w4011-20259343552.94.399366bdsq1Vjj&id=576658976732&rn=0633790eda22831641eefbffffe13730&abbucket=18&skuId=3803592381926","goodsId":323,"transport":0,"title":"精油5感香调，有一种香叫阿道夫","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1556100157283004.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100157614240.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100157875110.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100158322734.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100158872137.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100159518430.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100159729138.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100160054539.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100160779746.jpg\" alt=\"\" />","favoriteNum":1,"price":79,"supplier":"","name":"阿道夫 精油洗护专研洗发水（净屑舒爽）520ml","vipPrice":41.9,"wantNum":15,"sortBy":502,"categoryId":27,"sellNum":31,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548741415260951.png","compareUrl":"","goodsId":256,"transport":0,"title":"好用到连男朋友都抢着用 ","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547805267257275.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805267452272.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805267674808.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805267883550.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805268027601.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805268216702.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805268350307.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805268588386.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805268843046.png\" /><img src=\"http://image.bscvip.com/goods_images/1547805268945445.png\" />","favoriteNum":0,"price":119,"supplier":"","name":"Chicome 氨基酸抗氧化洁面乳","vipPrice":79,"wantNum":0,"sortBy":499,"categoryId":30,"sellNum":290,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548738607895301.png","compareUrl":"","goodsId":161,"transport":0,"title":"一个神奇的魔镜  照亮你的美","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1546185797598987.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185805697164.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185811409079.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185817381840.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185823312817.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185829278248.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185834457215.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185840234528.png\" /><img src=\"http://image.bscvip.com/goods_images/1546185845635160.png\" />","favoriteNum":0,"price":323,"supplier":"乔威潮品生活馆","name":"乔威 多功能化妆镜台灯","vipPrice":139.8,"wantNum":0,"sortBy":496,"categoryId":32,"sellNum":416,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548814061847091.jpg","compareUrl":"https://detail.yao.95095.com/item.htm?spm=a230r.1.14.15.15cf620ffkcg49&id=551967890739&cm_id=140105335569ed55e27b&abbucket=13&skuId=3541718530100","goodsId":219,"transport":0,"title":"从\u201c齿\u201d给宝宝分龄守护 适合6~12岁儿童","cartNum":0,"content":"<img src='http://image.bscvip.com/content_images/1553249338213778.jpg' /><img src='http://image.bscvip.com/content_images/1553249330838814.jpg' /><img src='http://image.bscvip.com/content_images/1553249324505750.jpg' /><img src='http://image.bscvip.com/content_images/1553249318335751.jpg' /><img src='http://image.bscvip.com/content_images/1553249311060956.jpg' /><img src='http://image.bscvip.com/content_images/1553249301212630.jpg' /><img src='http://image.bscvip.com/content_images/1553249295226962.jpg' /><img src='http://image.bscvip.com/content_images/1553249288516882.jpg' /><img src='http://image.bscvip.com/content_images/1553249276503370.jpg' /><img src='http://image.bscvip.com/content_images/1553249270696204.jpg' /><img src='http://image.bscvip.com/content_images/1553249263927040.jpg' />","favoriteNum":0,"price":15.8,"supplier":"南方医科大学研制","name":"洁灵 营养维C儿童牙膏（蓝莓）","vipPrice":14,"wantNum":0,"sortBy":410,"categoryId":31,"sellNum":549,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548813068120590.jpg","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-15415033326.114.42fa7150ZDldyj&id=43147505882&rn=ef74e1b6a67b2d959daf67757bc81a44&abbucket=1&skuId=74576690013","goodsId":218,"transport":0,"title":"可以吃的牙膏 不辣嘴的香甜草莓味 ","cartNum":0,"content":"<img src='http://image.bscvip.com/content_images/1554104028333254.jpg' /><img src='http://image.bscvip.com/content_images/1554110419792572.jpg' /><img src='http://image.bscvip.com/content_images/1554110431753354.jpg' /><img src='http://image.bscvip.com/content_images/1554098659524531.jpg' /><img src='http://image.bscvip.com/content_images/1554109238221884.jpg' /><img src='http://image.bscvip.com/content_images/1554111314675477.jpg' /><img src='http://image.bscvip.com/content_images/1554111383614406.jpg' /><img src='http://image.bscvip.com/content_images/1554111416475278.jpg' /><img src='http://image.bscvip.com/content_images/1554105960638569.jpg' /><img src='http://image.bscvip.com/content_images/1554103240314860.jpg' /><img src='http://image.bscvip.com/content_images/1554111776340536.jpg' /><img src='http://image.bscvip.com/content_images/1554112271443481.jpg' /><img src='http://image.bscvip.com/content_images/1554106869938824.jpg' /><img src='http://image.bscvip.com/content_images/1554101468412991.jpg' />","favoriteNum":0,"price":15.8,"supplier":"南方医科大学研制","name":"洁灵 健龈固齿儿童牙膏（草莓）","vipPrice":14,"wantNum":0,"sortBy":409,"categoryId":31,"sellNum":660,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548736306181627.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.9.591858b3hPBCDH&pvid=5394bbd4-4281-436d-93d5-94148a8d585e&pos=5&acm=03054.1003.1.2768562&id=556883144001&scm=1007.16862.95220.23864_0&utparam={%22x_hestia_source%22:%2223864%22,%22x_object_type%22:%22item%22,%22x_mt%22:0,%22x_src%22:%2223864%22,%22x_pos%22:5,%22x_pvid%22:%225394bbd4-4281-436d-93d5-94148a8d585e%22,%22x_object_id%22:556883144001}&skuId=3450492465352","goodsId":217,"transport":0,"title":"帮牙齿告别敏感 世界任我吃","cartNum":0,"content":"<img src='http://image.bscvip.com/content_images/1553583855749710.jpg' /><img src='http://image.bscvip.com/content_images/1553583848913404.jpg' /><img src='http://image.bscvip.com/content_images/1553583834574649.jpg' /><img src='http://image.bscvip.com/content_images/1553583827059537.jpg' /><img src='http://image.bscvip.com/content_images/1553583820245036.jpg' /><img src='http://image.bscvip.com/content_images/1553583812201995.jpg' /><img src='http://image.bscvip.com/content_images/1553583804606470.jpg' /><img src='http://image.bscvip.com/content_images/1553583795643868.jpg' /><img src='http://image.bscvip.com/content_images/1553583788881495.jpg' /><img src='http://image.bscvip.com/content_images/1553583782317435.jpg' /><img src='http://image.bscvip.com/content_images/1553583777043089.jpg' /><img src='http://image.bscvip.com/content_images/1553583770965136.jpg' />","favoriteNum":0,"price":35.5,"supplier":"南方医科大学研制","name":"洁灵 植物甙功效牙膏（改善牙齿敏感问题）","vipPrice":16,"wantNum":0,"sortBy":408,"categoryId":31,"sellNum":318,"freeShopping":80}],"name":"个护美妆","categoryId":3},{"titleImage":"http://image.bscvip.com/category_images/1557126244075830.png","image":"http://image.bscvip.com/category_images/1557126239676618.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1548738524567411.png","compareUrl":"","goodsId":167,"transport":0,"title":"手机半路没电 就跟车子半路没油一样绝望    ","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1546187115640811.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187122323811.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187128352589.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187134200497.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187140190007.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187146303927.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187152089270.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187158258597.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187165356495.png\" /><img src=\"http://image.bscvip.com/goods_images/1546187170578442.png\" />","favoriteNum":8,"price":89,"supplier":"QC3.0快充技术","name":"乔威 3USB车载充","vipPrice":59.9,"wantNum":0,"sortBy":500,"categoryId":38,"sellNum":355,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548738540985532.png","compareUrl":"","goodsId":166,"transport":0,"title":"它 音色迷人 不讲故事讲\u201c芯\u201d声","cartNum":0,"content":"<img src='http://image.bscvip.com/goods_images/1546186858554725.png' /><img src='http://image.bscvip.com/goods_images/1546186864186424.png' /><img src='http://image.bscvip.com/goods_images/1546186869669423.png' /><img src='http://image.bscvip.com/goods_images/1546186875871312.png' /><img src='http://image.bscvip.com/goods_images/1546186881078272.png' /><img src='http://image.bscvip.com/goods_images/1546186887141215.png' /><img src='http://image.bscvip.com/goods_images/1546186892312741.png' /><img src='http://image.bscvip.com/goods_images/1546186897405326.png' /><img src='http://image.bscvip.com/goods_images/1546186902660565.png' /><img src='http://image.bscvip.com/goods_images/1546186909226328.png' /><img src='http://image.bscvip.com/goods_images/1546186914607450.png' /><img src='http://image.bscvip.com/goods_images/1546186920325006.png' /><img src='http://image.bscvip.com/goods_images/1546186925809663.png' /><img src='http://image.bscvip.com/goods_images/1546186931519861.png' /><img src='http://image.bscvip.com/goods_images/1546186937025459.png' /><img src='http://image.bscvip.com/goods_images/1546186942189169.png' /><img src='http://image.bscvip.com/goods_images/1546186947326977.png' /><img src='http://image.bscvip.com/goods_images/1546186952699926.png' />","favoriteNum":0,"price":99,"supplier":"乔威潮品生活馆","name":"乔威 迷你布艺蓝牙音箱","vipPrice":59.9,"wantNum":0,"sortBy":499,"categoryId":40,"sellNum":268,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548738582527005.png","compareUrl":"","goodsId":163,"transport":0,"title":"百搭好音质 小个子大声扬","cartNum":0,"content":"<img src='http://image.bscvip.com/goods_images/1546186154021625.png' /><img src='http://image.bscvip.com/goods_images/1546186161245623.png' /><img src='http://image.bscvip.com/goods_images/1546186168783042.png' /><img src='http://image.bscvip.com/goods_images/1546186174051537.png' /><img src='http://image.bscvip.com/goods_images/1546186179345717.png' /><img src='http://image.bscvip.com/goods_images/1546186185694467.png' /><img src='http://image.bscvip.com/goods_images/1546186191385965.png' /><img src='http://image.bscvip.com/goods_images/1546186197666515.png' /><img src='http://image.bscvip.com/goods_images/1546186231151367.png' /><img src='http://image.bscvip.com/goods_images/1546186244033139.png' />","favoriteNum":0,"price":186,"supplier":"RDA5856蓝牙方案","name":"乔威 BM020蓝牙音响","vipPrice":91.8,"wantNum":0,"sortBy":498,"categoryId":40,"sellNum":265,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548738669977818.png","compareUrl":"","goodsId":157,"transport":0,"title":"更好记录美景 不错过旅途中的点滴","cartNum":0,"content":"<img src='http://image.bscvip.com/goods_images/1546185068925904.png' /><img src='http://image.bscvip.com/goods_images/1546185074190386.png' /><img src='http://image.bscvip.com/goods_images/1546185082549716.png' /><img src='http://image.bscvip.com/goods_images/1546185089008651.png' />","favoriteNum":0,"price":99,"supplier":"","name":"乔威 迷你口袋多功能自拍杆","vipPrice":52.8,"wantNum":0,"sortBy":496,"categoryId":39,"sellNum":223,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548738817239217.png","compareUrl":"","goodsId":145,"transport":0,"title":"单口充电更迅速 充电 我更专一 ","cartNum":0,"content":"<img src='http://image.bscvip.com/goods_images/1546182562036647.png' /><img src='http://image.bscvip.com/goods_images/1546182584810535.png' /><img src='http://image.bscvip.com/goods_images/1546182590632419.png' /><img src='http://image.bscvip.com/goods_images/1546182596730646.png' />","favoriteNum":2,"price":19.9,"supplier":"乔威工厂供应商","name":"乔威 2.1A单口充电器","vipPrice":13.9,"wantNum":0,"sortBy":494,"categoryId":38,"sellNum":365,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548738767727224.png","compareUrl":"","goodsId":149,"transport":0,"title":"带去旅行 苹果安卓一样用","cartNum":0,"content":"<img src='http://image.bscvip.com/goods_images/1546183353476102.png' /><img src='http://image.bscvip.com/goods_images/1546183358844701.png' /><img src='http://image.bscvip.com/goods_images/1546183365057568.png' /><img src='http://image.bscvip.com/goods_images/1546183370747714.png' /><img src='http://image.bscvip.com/goods_images/1546183379191145.png' /><img src='http://image.bscvip.com/goods_images/1546183385737065.png' /><img src='http://image.bscvip.com/goods_images/1546183392675350.png' /><img src='http://image.bscvip.com/goods_images/1546183399159883.png' /><img src='http://image.bscvip.com/goods_images/1546183405060299.png' />","favoriteNum":0,"price":35,"supplier":"一头两用 便捷共享","name":"乔威 苹果安卓二合一数据线  Li122","vipPrice":21.9,"wantNum":0,"sortBy":485,"categoryId":38,"sellNum":656,"freeShopping":80}],"name":"3C数码","categoryId":4},{"titleImage":"http://image.bscvip.com/category_images/1557126257797693.png","image":"http://image.bscvip.com/category_images/1557124958769423.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1553140451537222.jpg","compareUrl":"https://item.taobao.com/item.htm?spm=a1z10.5-c.w4002-18361640316.24.1bf5457fT6JWSp&id=567721790538","goodsId":310,"transport":0,"title":"防臭抗菌袜 远离细菌侵扰的烦恼","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553140638081281.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140630934052.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140623900338.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140616004143.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140607063195.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140598426829.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140591907223.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140584305413.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140576768757.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140569559838.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140562369573.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140553675743.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140546235258.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140539514559.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140531259829.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140524004581.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140516558889.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140508806126.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140501873601.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553140495367502.jpg\" />","favoriteNum":0,"price":109,"supplier":"","name":"TEAL 银离子抗菌袜中短筒网眼透气女士休闲棉袜","vipPrice":41.8,"wantNum":0,"sortBy":500,"categoryId":52,"sellNum":37,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553139206714017.jpg","compareUrl":"https://item.taobao.com/item.htm?spm=a1z10.5-c.w4002-18361640316.27.1bf5457fT6JWSp&id=567441423828","goodsId":307,"transport":0,"title":"防臭抗菌袜 远离细菌侵扰的烦恼","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553138255363140.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138248980876.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138242584240.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138235429213.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138227440072.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138199745977.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138193064112.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138186100893.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138178673453.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138172134227.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138164172268.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138157365121.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138149440757.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138142854481.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138136601430.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138130229466.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138122384967.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138115732899.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138108776545.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553138099759531.jpg\" />","favoriteNum":0,"price":109,"supplier":"","name":"TEAL 银基抑菌防臭浅口隐形女袜","vipPrice":41.8,"wantNum":0,"sortBy":499,"categoryId":52,"sellNum":37,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548740242786493.png","compareUrl":"https://item.taobao.com/item.htm?id=535959660115&ali_refid=a3_430582_1006:1102209785:N:%25E7%25B4%25AB%25E5%25A4%2596%25E7%25BA%25BF%25E9%2598%25B2%25E6%2599%2592%25E5%25B8%25BD:89be6053e733662e946a4f114045d62b&ali_trackid=1_89be6053e733662e946a4f114045d62b&spm=a230r.1.14.1#detail","goodsId":71,"transport":0,"title":"遮脸算啥？我们遮肩膀  ","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1546140566232105.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140571753532.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140577477445.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140586165778.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140592922089.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140598319416.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140604249561.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140609964771.png\" /><img src=\"http://image.bscvip.com/goods_images/1546140615503245.png\" />","favoriteNum":1,"price":39.9,"supplier":"5色户外大帽檐防晒帽","name":"户外骑行沙滩遮阳帽 超大帽檐带蝴蝶结防紫外线","vipPrice":15.9,"wantNum":0,"sortBy":497,"categoryId":36,"sellNum":362,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553140142262314.jpg","compareUrl":"https://item.jd.com/34978165521.html#","goodsId":309,"transport":0,"title":"防臭抗菌袜 远离细菌侵扰的烦恼","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553139992670892.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139985696992.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139976804767.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139968616700.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139956258256.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139946671207.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139940034087.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139931765296.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139923965526.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139914115016.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139905394381.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139896740964.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139888709718.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139882242853.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139875293246.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139865792088.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139855288903.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139844476212.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139837774487.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553139828141630.jpg\" />","favoriteNum":1,"price":109,"supplier":"","name":"TEAL 防臭运动棉袜男士袜子四季短筒","vipPrice":41.8,"wantNum":0,"sortBy":496,"categoryId":52,"sellNum":262,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553239677155185.jpg","compareUrl":"https://item.taobao.com/item.htm?spm=a230r.1.14.16.67ef479aqTzFHy&id=574943464134&ns=1&abbucket=10#detail","goodsId":209,"transport":0,"title":"贴身的 就要贴心的柔棉品质","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1546588308440811.png\" /><img src=\"http://image.bscvip.com/goods_images/1546588308672120.png\" /><img src=\"http://image.bscvip.com/goods_images/1546588309242153.png\" /><img src=\"http://image.bscvip.com/goods_images/1546588309841105.png\" /><img src=\"http://image.bscvip.com/goods_images/1546588310237824.png\" /><img src=\"http://image.bscvip.com/goods_images/1546588311033435.png\" /><img src=\"http://image.bscvip.com/goods_images/1546588312036056.png\" />","favoriteNum":2,"price":48,"supplier":"专业内裤制造商","name":"纯棉男童平角裤 纯色裤头青少年四角内裤","vipPrice":33,"wantNum":0,"sortBy":300,"categoryId":36,"sellNum":223,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1556165007747904.jpg","compareUrl":"https://item.jd.com/41577869891.html","goodsId":335,"transport":0,"title":"舒适无痕面料 软滑透气 呵护肌肤","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1556099839117837.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099839532084.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099839872737.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099840366334.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099840705665.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099840956558.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099841255208.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099841535175.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099841887297.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099842661154.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099842960200.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099843468138.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099844161950.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099844572976.jpg\" alt=\"\" />","favoriteNum":0,"price":69.9,"supplier":"","name":"猫人男士内裤男平角裤纯棉中腰短裤头U凸透气中腰四角裤3条装 ","vipPrice":55.9,"wantNum":3733,"sortBy":11,"categoryId":36,"sellNum":159,"freeShopping":80}],"name":"服装配饰","categoryId":5},{"titleImage":"http://image.bscvip.com/category_images/1557126210189911.png","image":"http://image.bscvip.com/category_images/1557124889367190.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1553131585795096.jpg","compareUrl":"https://item.jd.com/100000559152.html#crumb-wrap","goodsId":299,"transport":0,"title":"双重锁定 有钥匙更安全","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553094558479922.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094510199140.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094487692334.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094468068958.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094450698018.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094432845273.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094412003298.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094397591243.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094384131594.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553094367918114.jpg\" />","favoriteNum":0,"price":69,"supplier":"","name":"贝得力 儿童防走失牵引绳","vipPrice":29.8,"wantNum":0,"sortBy":500,"categoryId":24,"sellNum":145,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553226328381666.jpg","compareUrl":"https://item.jd.com/11608393704.html","goodsId":313,"transport":0,"title":"","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553226205315184.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226198415305.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226190360399.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226183196751.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226175080307.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226164198634.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226137020928.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226131143923.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226124676377.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226116023981.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226109497176.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226103043918.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553226097493392.jpg\" />","favoriteNum":0,"price":39.9,"supplier":"","name":"天堂 儿童直杆晴雨伞手动免按款","vipPrice":29.8,"wantNum":0,"sortBy":500,"categoryId":23,"sellNum":14,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553738125990912.jpg","compareUrl":"https://chaoshi.detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.53a96465DCn5Mz&id=565190540070&skuId=3578754404771&areaId=440100&user_id=725677994&cat_id=2&is_b=1&rn=ea2b7faa6f1ce248267208ad923fbcd4","goodsId":300,"transport":0,"title":"防夹肉四件套 防尘易收纳","cartNum":0,"content":"<img src='http://image.bscvip.com/content_images/1553095208830029.jpg' /><img src='http://image.bscvip.com/content_images/1553095164752020.jpg' /><img src='http://image.bscvip.com/content_images/1553095146169744.jpg' /><img src='http://image.bscvip.com/content_images/1553095123964052.jpg' /><img src='http://image.bscvip.com/content_images/1553095106579380.jpg' /><img src='http://image.bscvip.com/content_images/1553095088996603.jpg' /><img src='http://image.bscvip.com/content_images/1553095062526708.jpg' /><img src='http://image.bscvip.com/content_images/1553095047805773.jpg' /><img src='http://image.bscvip.com/content_images/1553095030384347.jpg' /><img src='http://image.bscvip.com/content_images/1553095016813355.jpg' /><img src='http://image.bscvip.com/content_images/1553094976832239.jpg' /><img src='http://image.bscvip.com/content_images/1553094897518692.jpg' />","favoriteNum":0,"price":22.8,"supplier":"","name":"贝得力 婴儿指甲剪套装","vipPrice":15.9,"wantNum":0,"sortBy":499,"categoryId":24,"sellNum":39,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553738791701654.jpg","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.d80a7727EVhGqx&id=537040740968&skuId=3570098672873&areaId=440100&user_id=2960953862&cat_id=2&is_b=1&rn=32aa709cb806412dcb5109fabb9f5399","goodsId":297,"transport":0,"title":"一杯多盖设计 满足不同需求","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1553133088832735.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133081431178.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133073370413.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133064912452.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133056262165.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133046527543.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133040123814.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133033606239.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133024776260.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133017020588.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553133009764795.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553132995829917.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553132989181487.jpg\" /><img src=\"http://image.bscvip.com/content_images/1553132980385719.jpg\" />","favoriteNum":0,"price":198,"supplier":"","name":"杯具熊 儿童保温杯三盖款礼盒装","vipPrice":119.9,"wantNum":0,"sortBy":497,"categoryId":23,"sellNum":170,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553765527219705.jpg","compareUrl":"https://chaoshi.detail.tmall.com/item.htm?spm=a220m.1000858.1000725.6.419543a0e5DLs8&id=527764728601&skuId=3952397918588&areaId=440100&user_id=725677994&cat_id=2&is_b=1&rn=989d32395251ec46b2a742d56677d7cd","goodsId":226,"transport":0,"title":"以后不用辛苦起泡啦~国外潮妈都爱用的\u201c慕斯型\u201d 宝宝睁眼也能洗","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547090825613458.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090825753284.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090825973060.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090826140456.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090826293350.png\" />","favoriteNum":0,"price":29,"supplier":"一瓶两用 天然提取","name":"子初 婴儿倍护洗发沐浴露 250ml","vipPrice":20.9,"wantNum":0,"sortBy":493,"categoryId":24,"sellNum":264,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548734328727966.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.351169228IHqOr&id=539753809425&areaId=440100&is_b=1&cat_id=2&q=%D7%D3%B3%F5+%D3%A4%B6%F9+%C3%E6%CB%AA","goodsId":221,"transport":0,"title":"寒风起 面霜囤起来 看我把\u201c红苹果\u201d吃光光","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547090029757451.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090029965444.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090030167183.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090030392210.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090030624319.png\" /><img src=\"http://image.bscvip.com/goods_images/1547090030844833.png\" />","favoriteNum":1,"price":28,"supplier":"万千妈妈口碑加冕","name":"子初 婴儿倍护滋养面霜 70g","vipPrice":23.9,"wantNum":0,"sortBy":429,"categoryId":24,"sellNum":152,"freeShopping":80}],"name":"母婴用品","categoryId":2},{"titleImage":"http://image.bscvip.com/category_images/1557126265145926.png","image":"http://image.bscvip.com/category_images/1557124972955401.png","goodsList":[{"image":"http://image.bscvip.com/goods_images/1555555109974677.png","compareUrl":"","goodsId":322,"transport":0,"title":"跟闺密一起追剧，怎么可以少了零嘴呢！","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1556100225977979.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100226740029.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100228067152.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100228977016.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100229545688.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100230256719.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100230964855.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100231420049.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100232459792.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100233261827.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100234770457.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100235683690.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100237482020.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100238573990.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100245810325.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100246607404.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100248319861.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100249783990.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100251129266.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100252031386.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100253495201.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100254394131.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100256041585.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100256982541.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100257403241.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556100258533789.png\" alt=\"\" />","favoriteNum":0,"price":49.9,"supplier":"","name":"麦德好 零食混合装 雪花酥/话梅糖/芒果条/谷物米酥  2包混合","vipPrice":39.9,"wantNum":4,"sortBy":452,"categoryId":42,"sellNum":270,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548731025746802.png","compareUrl":"","goodsId":272,"transport":0,"title":"称心好礼 给在意的你 ","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547882716305899.png\" /><img src=\"http://image.bscvip.com/goods_images/1547882716495362.png\" /><img src=\"http://image.bscvip.com/goods_images/1547882716678490.png\" /><img src=\"http://image.bscvip.com/goods_images/1547882716972616.png\" /><img src=\"http://image.bscvip.com/goods_images/1547882717306451.png\" /><img src=\"http://image.bscvip.com/goods_images/1547882717507488.png\" /><img src=\"http://image.bscvip.com/goods_images/1547882717687146.png\" />","favoriteNum":0,"price":128,"supplier":"AAA级坚果 礼品优选","name":"坚果礼盒零食大礼包 内含14袋","vipPrice":79.9,"wantNum":0,"sortBy":394,"categoryId":42,"sellNum":189,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1556164694391473.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-15820046402.69.1b58a71aLh9rij&id=578838532976&rn=5d765c53b54f2740b004177bd08f3046&abbucket=13","goodsId":344,"transport":0,"title":"果香浓郁 入口醇厚 适合女生喝的酒","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1556099541597202.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099542201445.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099542369768.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099542733658.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099542963370.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099543435790.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099544007841.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099545078939.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099545371126.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099546419648.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1556099546606151.jpg\" alt=\"\" />","favoriteNum":0,"price":169,"supplier":"","name":"慕拉桃子酒女生女士低度水果酒桃花酿葡萄酒甜酒桃花醉葡乐贝利尼","vipPrice":51.9,"wantNum":862,"sortBy":100,"categoryId":47,"sellNum":76,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1553765985327092.jpg","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-14717307369.51.5d1829e6sJKkDd&id=540230115948&rn=095e2e2a88a541b5a2c8bcd9c71a8eab&abbucket=13&skuId=4127789239068","goodsId":230,"transport":0,"title":"猪年网红礼盒双口味任选 满足每一张挑剔的嘴巴     ","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547199505759072.png\" /><img src=\"http://image.bscvip.com/goods_images/1547199505965722.png\" /><img src=\"http://image.bscvip.com/goods_images/1547199506178998.png\" /><img src=\"http://image.bscvip.com/goods_images/1547199506405252.png\" /><img src=\"http://image.bscvip.com/goods_images/1547199506706350.png\" /><img src=\"http://image.bscvip.com/goods_images/1547199507034248.png\" />","favoriteNum":3,"price":48,"supplier":"","name":"怡浓 网红猪你快乐幸福棒棒糖牛奶巧克力礼盒装送女友儿童 ","vipPrice":31.9,"wantNum":0,"sortBy":100,"categoryId":7,"sellNum":221,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1555552975073737.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a230r.1.14.1.ba8c1f3f68OSyB&id=555000696101&cm_id=140105335569ed55e27b&abbucket=13&skuId=3423722801219","goodsId":291,"transport":0,"title":"执着的芒果干 始终坚持原味","cartNum":0,"content":"<img src='http://image.bscvip.com/goods_images/1551779826663989.png' /><img src='http://image.bscvip.com/goods_images/1551779826860917.png' /><img src='http://image.bscvip.com/goods_images/1551779827052607.png' /><img src='http://image.bscvip.com/goods_images/1551779827423428.png' /><img src='http://image.bscvip.com/goods_images/1551779828248244.png' /><img src='http://image.bscvip.com/goods_images/1551779828707099.png' /><img src='http://image.bscvip.com/goods_images/1551779829563656.png' /><img src='http://image.bscvip.com/goods_images/1551779830220812.png' /><img src='http://image.bscvip.com/goods_images/1551779830613350.png' /><img src='http://image.bscvip.com/goods_images/1551779830788580.png' /><img src='http://image.bscvip.com/goods_images/1551779831673706.png' /><img src='http://image.bscvip.com/goods_images/1551779832119973.png' />","favoriteNum":1,"price":49.9,"supplier":"","name":"麦德好 泰芒功夫芒果条 2包","vipPrice":39.9,"wantNum":25,"sortBy":99,"categoryId":42,"sellNum":181,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1548731095172130.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.81.14a01cbbHpdbBB&id=560611553567&areaId=440100&user_id=3163390723&cat_id=2&is_b=1&rn=a8509e58701fae9488eca2d2ad194183","goodsId":271,"transport":0,"title":"十人九湿 不做老湿机 一年四季都适合喝的代餐粉 ","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547873478577034.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873478794856.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479004245.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479124231.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479260219.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479417823.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479587261.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479727306.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873479898352.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873480139297.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873480513437.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873480661942.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873480951923.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873481124492.png\" /><img src=\"http://image.bscvip.com/goods_images/1547873481320504.png\" />","favoriteNum":0,"price":78,"supplier":"27道工序匠制","name":"佰草汇 红豆薏米燕麦枸杞粉","vipPrice":32.9,"wantNum":0,"sortBy":96,"categoryId":48,"sellNum":150,"freeShopping":80}],"name":"休闲食品","categoryId":7}]
     * unreadNum : 0
     * redPack : 1
     * rushBuy : {"goodsList":[{"image":"http://image.bscvip.com/goods_images/1557049170710630.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-16655872867.151.f79f2230FT3AVk&id=566103848277&rn=e23e3f4655b623834640daff384993a0&abbucket=18&skuId=4123636291091","goodsId":414,"rushBuyId":16,"transport":0,"shareMoney":0.8,"title":"专业矫正握姿","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557049419696514.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049420277096.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049420782704.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049421328146.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049421937720.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049422482276.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049423039242.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049423606466.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049423791008.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049424361629.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049424931219.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049425483121.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049426080988.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049426241224.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049426803075.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049427343378.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049427489571.jpg\" alt=\"\" />","favoriteNum":0,"price":68,"supplier":"","name":"猫太子学生铅笔握笔矫正器 三指软胶握把笔套批发 硅胶儿童握笔器","vipPrice":17.8,"wantNum":14,"sortBy":9,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":200,"categoryId":-2,"sellNum":36,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051177956452.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-16655872867.320.5f3f2230rqUTbY&id=43003509025&rn=f698d1aab407f0d30294e8484816c66e&abbucket=18","goodsId":415,"rushBuyId":16,"transport":0,"shareMoney":2.9,"title":"入学之前练一手好字","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051186294184.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051186442427.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051186560851.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051186698675.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051187211106.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051187683457.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051187822270.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051188359711.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051188857863.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051189342945.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051189879193.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051190405043.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051190916198.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051191503014.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051198894431.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051199068749.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051199174344.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051199684579.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200137498.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200267785.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200734423.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200866567.jpg\" alt=\"\" />","favoriteNum":0,"price":168,"supplier":"","name":"猫太子 28天练字板 儿童启蒙版识字练字描红本套装益智学习玩具  ","vipPrice":59.9,"wantNum":57,"sortBy":8,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":120,"categoryId":-2,"sellNum":69,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051415080337.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.38512b04TCL8hz&id=44545932349&skuId=4163402554970&areaId=440100&user_id=2115001742&cat_id=2&is_b=1&rn=00a706d635869c960ad1b71c660731fa","goodsId":416,"rushBuyId":16,"transport":0,"shareMoney":2.9,"title":"改善孩子不良坐姿","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051422061933.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051422171088.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051422704813.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051423238094.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051423748660.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051423949281.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051424917352.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051425455983.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051425997599.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051426166181.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051426647915.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051427181414.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051427680403.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051428245239.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051428398751.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051428907951.jpg\" alt=\"\" />","favoriteNum":0,"price":116,"supplier":"","name":"猫太子坐姿矫正器 护眼写字架多功能视力保护器坐姿纠正支架","vipPrice":59.9,"wantNum":45,"sortBy":7,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":200,"categoryId":-2,"sellNum":64,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051663398623.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.38512b04TCL8hz&id=44545932349&skuId=4163402554973&areaId=440100&user_id=2115001742&cat_id=2&is_b=1&rn=00a706d635869c960ad1b71c660731fa","goodsId":417,"rushBuyId":16,"transport":0,"shareMoney":1.9,"title":"语音夹桌款 改善孩子不良坐姿","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051670460238.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051670577333.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051670706142.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051670769344.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671219447.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671318827.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671443100.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671860343.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051672325940.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051672473487.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051672949494.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051673086301.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051673175478.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681358053.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681449273.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681581416.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681645255.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681770097.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681840694.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681914425.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682413341.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682590693.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682709555.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682775757.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682841604.jpg\" alt=\"\" />","favoriteNum":0,"price":116,"supplier":"","name":"猫太子 坐姿矫正器学生儿童 防近视架 视力保护器写字架","vipPrice":49.9,"wantNum":14,"sortBy":6,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":200,"categoryId":-2,"sellNum":36,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051925011926.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.16.12d029275o7Som&id=589669287998&skuId=4030983441143&areaId=440100&user_id=2200674092537&cat_id=2&is_b=1&rn=9c664dbcaef7e13c3c2bd3eecc3c488b","goodsId":418,"rushBuyId":16,"transport":0,"shareMoney":1.8,"title":"拒水成珠 一甩即干","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051931620500.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931709434.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931776124.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931841254.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931903071.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931975000.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932406443.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932525966.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932640619.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932699835.jpg\" alt=\"\" />","favoriteNum":0,"price":69.9,"supplier":"0","name":"日系小清新16K直杆伞长柄雨伞简约纯色皮手柄伞复古男女雨伞","vipPrice":35.8,"wantNum":0,"sortBy":5,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":500,"categoryId":-2,"sellNum":14,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557052291490201.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.4eda2feeonUiWf&id=574424406171&skuId=3975466951620&areaId=440100&is_b=1&cat_id=2&q=kd%20036","goodsId":419,"rushBuyId":16,"transport":0,"shareMoney":9.9,"title":"让时光倒流 容颜依旧","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557052298201138.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298320166.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298458766.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298592147.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298716147.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299139353.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299258453.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299730369.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299978272.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052300468597.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301018910.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301158644.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301669834.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301842738.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301925849.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052302005862.jpg\" alt=\"\" />","favoriteNum":0,"price":999,"supplier":"","name":"金稻KD-036面部光疗光子嫩肤LED红光面罩祛斑蓝光美容仪","vipPrice":259,"wantNum":96,"sortBy":4,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":120,"categoryId":-2,"sellNum":73,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557052493045700.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.19c3552evVywEA&id=575122852660&skuId=3941132082702&areaId=440100&user_id=2068495324&cat_id=2&is_b=1&rn=aa4f6f83a8ea1716c84c68c28fb1a434","goodsId":420,"rushBuyId":16,"transport":0,"shareMoney":1.8,"title":"水过无痕 易于清洗","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557052501867279.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052501974406.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052502049920.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052502165223.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052502611781.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052503583887.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052503804647.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052504272515.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052504869983.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052504988807.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052505120380.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052505249766.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052505746981.jpg\" alt=\"\" />","favoriteNum":0,"price":99.8,"supplier":"","name":"天然硅藻土脚垫吸水速干 浴室防滑地垫硅藻泥脚垫","vipPrice":31.8,"wantNum":145,"sortBy":3,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":943,"categoryId":-2,"sellNum":145,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557053314383004.png","compareUrl":"https://item.jd.com/34556700330.html","goodsId":421,"rushBuyId":16,"transport":0,"shareMoney":1.9,"title":"双层带抽屉","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557053322057931.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322183225.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322254049.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322730573.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322862046.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053323338242.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053323823205.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053323972793.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053324450283.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053324570786.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325074114.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325583165.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325778307.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325873629.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053326393545.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053326557776.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053327033685.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053327483405.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053327598908.jpg\" alt=\"\" />","favoriteNum":0,"price":76,"supplier":"","name":"ecoco意可可双层纸巾盒置物架卫生间厕所纸盒家用免打孔创意防水","vipPrice":35.9,"wantNum":67,"sortBy":2,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":188,"categoryId":-2,"sellNum":67,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557053531069862.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.291b28feHav2Mb&id=580011015215&skuId=4024138686311&areaId=440100&is_b=1&cat_id=2&q=auberge+%25B0%25AC%25B1%25C8+%25C7%25FD%25CE%25C3+%25CA%25D6%25BB%25B7","goodsId":422,"rushBuyId":16,"transport":0,"shareMoney":2.8,"title":"杯具熊联名款 AUB-70X（标准款）","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557053540220610.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053541105427.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053543023246.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053544735082.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053546725973.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053547534395.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053549590776.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053550812629.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053551893398.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053552090663.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053554083633.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053554327560.jpg\" alt=\"\" />","favoriteNum":0,"price":422,"supplier":"","name":"Auberge 法国艾比驱蚊手环单条装3驱蚊片 儿童婴儿孕妇防蚊手环 ","vipPrice":79.9,"wantNum":64,"sortBy":1,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":167,"categoryId":-2,"sellNum":64,"freeShopping":80}],"name":"10:00","rushBuyId":16,"sortBy":14,"startTime":1557108000000,"endTime":1557244500000}
     * redPackMoney : 3.28
     * defaultAddress : true
     */
    private RedGoodsBean redGoods;
    private int unreadNum;
    private int redPack;
    private RushBuyBean rushBuy;//限时抢购
    private String redPackMoney;
    private boolean defaultAddress;
    private List<CategoryListBeanX> categoryList;//小分类
    private List<AdvBannerListBean> advBannerList;//轮播图
    private List<GoodsCategoryListBean> goodsCategoryList;

    public RedGoodsBean getRedGoods() {
        return redGoods;
    }

    public void setRedGoods(RedGoodsBean redGoods) {
        this.redGoods = redGoods;
    }

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public int getRedPack() {
        return redPack;
    }

    public void setRedPack(int redPack) {
        this.redPack = redPack;
    }

    public RushBuyBean getRushBuy() {
        return rushBuy;
    }

    public void setRushBuy(RushBuyBean rushBuy) {
        this.rushBuy = rushBuy;
    }

    public String getRedPackMoney() {
        return redPackMoney;
    }

    public void setRedPackMoney(String redPackMoney) {
        this.redPackMoney = redPackMoney;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public List<CategoryListBeanX> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBeanX> categoryList) {
        this.categoryList = categoryList;
    }

    public List<AdvBannerListBean> getAdvBannerList() {
        return advBannerList;
    }

    public void setAdvBannerList(List<AdvBannerListBean> advBannerList) {
        this.advBannerList = advBannerList;
    }

    public List<GoodsCategoryListBean> getGoodsCategoryList() {
        return goodsCategoryList;
    }

    public void setGoodsCategoryList(List<GoodsCategoryListBean> goodsCategoryList) {
        this.goodsCategoryList = goodsCategoryList;
    }

    public static class RedGoodsBean{
        private String image;
        private String title;
        private String vipPrice;
        private String name;
        private int goodsId;


        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(String vipPrice) {
            this.vipPrice = vipPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }
    }


    public static class RushBuyBean {
        /**
         * goodsList : [{"image":"http://image.bscvip.com/goods_images/1557049170710630.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-16655872867.151.f79f2230FT3AVk&id=566103848277&rn=e23e3f4655b623834640daff384993a0&abbucket=18&skuId=4123636291091","goodsId":414,"rushBuyId":16,"transport":0,"shareMoney":0.8,"title":"专业矫正握姿","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557049419696514.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049420277096.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049420782704.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049421328146.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049421937720.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049422482276.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049423039242.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049423606466.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049423791008.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049424361629.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049424931219.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049425483121.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049426080988.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049426241224.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049426803075.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049427343378.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557049427489571.jpg\" alt=\"\" />","favoriteNum":0,"price":68,"supplier":"","name":"猫太子学生铅笔握笔矫正器 三指软胶握把笔套批发 硅胶儿童握笔器","vipPrice":17.8,"wantNum":14,"sortBy":9,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":200,"categoryId":-2,"sellNum":36,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051177956452.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-16655872867.320.5f3f2230rqUTbY&id=43003509025&rn=f698d1aab407f0d30294e8484816c66e&abbucket=18","goodsId":415,"rushBuyId":16,"transport":0,"shareMoney":2.9,"title":"入学之前练一手好字","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051186294184.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051186442427.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051186560851.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051186698675.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051187211106.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051187683457.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051187822270.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051188359711.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051188857863.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051189342945.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051189879193.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051190405043.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051190916198.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051191503014.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051198894431.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051199068749.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051199174344.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051199684579.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200137498.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200267785.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200734423.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051200866567.jpg\" alt=\"\" />","favoriteNum":0,"price":168,"supplier":"","name":"猫太子 28天练字板 儿童启蒙版识字练字描红本套装益智学习玩具  ","vipPrice":59.9,"wantNum":57,"sortBy":8,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":120,"categoryId":-2,"sellNum":69,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051415080337.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.38512b04TCL8hz&id=44545932349&skuId=4163402554970&areaId=440100&user_id=2115001742&cat_id=2&is_b=1&rn=00a706d635869c960ad1b71c660731fa","goodsId":416,"rushBuyId":16,"transport":0,"shareMoney":2.9,"title":"改善孩子不良坐姿","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051422061933.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051422171088.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051422704813.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051423238094.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051423748660.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051423949281.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051424917352.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051425455983.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051425997599.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051426166181.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051426647915.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051427181414.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051427680403.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051428245239.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051428398751.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051428907951.jpg\" alt=\"\" />","favoriteNum":0,"price":116,"supplier":"","name":"猫太子坐姿矫正器 护眼写字架多功能视力保护器坐姿纠正支架","vipPrice":59.9,"wantNum":45,"sortBy":7,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":200,"categoryId":-2,"sellNum":64,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051663398623.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.38512b04TCL8hz&id=44545932349&skuId=4163402554973&areaId=440100&user_id=2115001742&cat_id=2&is_b=1&rn=00a706d635869c960ad1b71c660731fa","goodsId":417,"rushBuyId":16,"transport":0,"shareMoney":1.9,"title":"语音夹桌款 改善孩子不良坐姿","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051670460238.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051670577333.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051670706142.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051670769344.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671219447.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671318827.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671443100.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051671860343.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051672325940.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051672473487.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051672949494.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051673086301.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051673175478.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681358053.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681449273.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681581416.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681645255.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681770097.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681840694.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051681914425.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682413341.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682590693.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682709555.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682775757.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051682841604.jpg\" alt=\"\" />","favoriteNum":0,"price":116,"supplier":"","name":"猫太子 坐姿矫正器学生儿童 防近视架 视力保护器写字架","vipPrice":49.9,"wantNum":14,"sortBy":6,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":200,"categoryId":-2,"sellNum":36,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557051925011926.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.16.12d029275o7Som&id=589669287998&skuId=4030983441143&areaId=440100&user_id=2200674092537&cat_id=2&is_b=1&rn=9c664dbcaef7e13c3c2bd3eecc3c488b","goodsId":418,"rushBuyId":16,"transport":0,"shareMoney":1.8,"title":"拒水成珠 一甩即干","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557051931620500.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931709434.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931776124.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931841254.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931903071.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051931975000.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932406443.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932525966.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932640619.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557051932699835.jpg\" alt=\"\" />","favoriteNum":0,"price":69.9,"supplier":"0","name":"日系小清新16K直杆伞长柄雨伞简约纯色皮手柄伞复古男女雨伞","vipPrice":35.8,"wantNum":0,"sortBy":5,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":500,"categoryId":-2,"sellNum":14,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557052291490201.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.4eda2feeonUiWf&id=574424406171&skuId=3975466951620&areaId=440100&is_b=1&cat_id=2&q=kd%20036","goodsId":419,"rushBuyId":16,"transport":0,"shareMoney":9.9,"title":"让时光倒流 容颜依旧","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557052298201138.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298320166.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298458766.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298592147.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052298716147.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299139353.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299258453.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299730369.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052299978272.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052300468597.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301018910.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301158644.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301669834.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301842738.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052301925849.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052302005862.jpg\" alt=\"\" />","favoriteNum":0,"price":999,"supplier":"","name":"金稻KD-036面部光疗光子嫩肤LED红光面罩祛斑蓝光美容仪","vipPrice":259,"wantNum":96,"sortBy":4,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":120,"categoryId":-2,"sellNum":73,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557052493045700.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.1.19c3552evVywEA&id=575122852660&skuId=3941132082702&areaId=440100&user_id=2068495324&cat_id=2&is_b=1&rn=aa4f6f83a8ea1716c84c68c28fb1a434","goodsId":420,"rushBuyId":16,"transport":0,"shareMoney":1.8,"title":"水过无痕 易于清洗","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557052501867279.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052501974406.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052502049920.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052502165223.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052502611781.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052503583887.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052503804647.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052504272515.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052504869983.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052504988807.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052505120380.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052505249766.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557052505746981.jpg\" alt=\"\" />","favoriteNum":0,"price":99.8,"supplier":"","name":"天然硅藻土脚垫吸水速干 浴室防滑地垫硅藻泥脚垫","vipPrice":31.8,"wantNum":145,"sortBy":3,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":943,"categoryId":-2,"sellNum":145,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557053314383004.png","compareUrl":"https://item.jd.com/34556700330.html","goodsId":421,"rushBuyId":16,"transport":0,"shareMoney":1.9,"title":"双层带抽屉","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557053322057931.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322183225.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322254049.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322730573.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053322862046.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053323338242.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053323823205.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053323972793.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053324450283.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053324570786.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325074114.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325583165.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325778307.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053325873629.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053326393545.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053326557776.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053327033685.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053327483405.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053327598908.jpg\" alt=\"\" />","favoriteNum":0,"price":76,"supplier":"","name":"ecoco意可可双层纸巾盒置物架卫生间厕所纸盒家用免打孔创意防水","vipPrice":35.9,"wantNum":67,"sortBy":2,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":188,"categoryId":-2,"sellNum":67,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1557053531069862.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.291b28feHav2Mb&id=580011015215&skuId=4024138686311&areaId=440100&is_b=1&cat_id=2&q=auberge+%25B0%25AC%25B1%25C8+%25C7%25FD%25CE%25C3+%25CA%25D6%25BB%25B7","goodsId":422,"rushBuyId":16,"transport":0,"shareMoney":2.8,"title":"杯具熊联名款 AUB-70X（标准款）","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1557053540220610.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053541105427.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053543023246.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053544735082.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053546725973.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053547534395.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053549590776.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053550812629.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053551893398.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053552090663.jpg\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053554083633.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1557053554327560.jpg\" alt=\"\" />","favoriteNum":0,"price":422,"supplier":"","name":"Auberge 法国艾比驱蚊手环单条装3驱蚊片 儿童婴儿孕妇防蚊手环 ","vipPrice":79.9,"wantNum":64,"sortBy":1,"startTime":1557108000000,"currTime":1557132617000,"endTime":1557244500000,"stock":167,"categoryId":-2,"sellNum":64,"freeShopping":80}]
         * name : 10:00
         * rushBuyId : 16
         * sortBy : 14
         * startTime : 1557108000000
         * endTime : 1557244500000
         */

        private String name;
        private int rushBuyId;
        private int sortBy;
        private long startTime;
        private long endTime;
        private List<GoodsListBean> goodsList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRushBuyId() {
            return rushBuyId;
        }

        public void setRushBuyId(int rushBuyId) {
            this.rushBuyId = rushBuyId;
        }

        public int getSortBy() {
            return sortBy;
        }

        public void setSortBy(int sortBy) {
            this.sortBy = sortBy;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * image : http://image.bscvip.com/goods_images/1557049170710630.png
             * compareUrl : https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-16655872867.151.f79f2230FT3AVk&id=566103848277&rn=e23e3f4655b623834640daff384993a0&abbucket=18&skuId=4123636291091
             * goodsId : 414
             * rushBuyId : 16
             * transport : 0
             * shareMoney : 0.8
             * title : 专业矫正握姿
             * cartNum : 0
             * content : <img src="http://image.bscvip.com/goods_images/1557049419696514.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049420277096.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049420782704.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049421328146.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049421937720.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049422482276.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049423039242.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049423606466.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049423791008.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049424361629.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049424931219.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049425483121.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049426080988.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049426241224.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049426803075.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049427343378.jpg" alt="" /><img src="http://image.bscvip.com/goods_images/1557049427489571.jpg" alt="" />
             * favoriteNum : 0
             * price : 68
             * supplier :
             * name : 猫太子学生铅笔握笔矫正器 三指软胶握把笔套批发 硅胶儿童握笔器
             * vipPrice : 17.8
             * wantNum : 14
             * sortBy : 9
             * startTime : 1557108000000
             * currTime : 1557132617000
             * endTime : 1557244500000
             * stock : 200
             * categoryId : -2
             * sellNum : 36
             * freeShopping : 80
             */

            private String image;
            private String compareUrl;
            private int goodsId;
            private int rushBuyId;
            private int transport;
            private double shareMoney;
            private String title;
            private int cartNum;
            private String content;
            private int favoriteNum;
            private String price;
            private String supplier;
            private String name;
            private String vipPrice;
            private int wantNum;
            private int sortBy;
            private long startTime;
            private long currTime;
            private long endTime;
            private int stock;
            private int categoryId;
            private int sellNum;
            private int freeShopping;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getCompareUrl() {
                return compareUrl;
            }

            public void setCompareUrl(String compareUrl) {
                this.compareUrl = compareUrl;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getRushBuyId() {
                return rushBuyId;
            }

            public void setRushBuyId(int rushBuyId) {
                this.rushBuyId = rushBuyId;
            }

            public int getTransport() {
                return transport;
            }

            public void setTransport(int transport) {
                this.transport = transport;
            }

            public double getShareMoney() {
                return shareMoney;
            }

            public void setShareMoney(double shareMoney) {
                this.shareMoney = shareMoney;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCartNum() {
                return cartNum;
            }

            public void setCartNum(int cartNum) {
                this.cartNum = cartNum;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getFavoriteNum() {
                return favoriteNum;
            }

            public void setFavoriteNum(int favoriteNum) {
                this.favoriteNum = favoriteNum;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSupplier() {
                return supplier;
            }

            public void setSupplier(String supplier) {
                this.supplier = supplier;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVipPrice() {
                return vipPrice;
            }

            public void setVipPrice(String vipPrice) {
                this.vipPrice = vipPrice;
            }

            public int getWantNum() {
                return wantNum;
            }

            public void setWantNum(int wantNum) {
                this.wantNum = wantNum;
            }

            public int getSortBy() {
                return sortBy;
            }

            public void setSortBy(int sortBy) {
                this.sortBy = sortBy;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getCurrTime() {
                return currTime;
            }

            public void setCurrTime(long currTime) {
                this.currTime = currTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public int getSellNum() {
                return sellNum;
            }

            public void setSellNum(int sellNum) {
                this.sellNum = sellNum;
            }

            public int getFreeShopping() {
                return freeShopping;
            }

            public void setFreeShopping(int freeShopping) {
                this.freeShopping = freeShopping;
            }
        }
    }

    public static class CategoryListBeanX {
        /**
         * image : http://image.bscvip.com/category_images/1557124873204656.png
         * categoryList : [{"image":"http://image.bscvip.com/category_images/1554283956738870.jpg","name":"酒水饮料","categoryId":41}]
         * name : 热门
         * categoryId : 1
         */

        private String image;
        private String name;
        private int categoryId;
        private List<CategoryListBean> categoryList;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public static class CategoryListBean {
            /**
             * image : http://image.bscvip.com/category_images/1554283956738870.jpg
             * name : 酒水饮料
             * categoryId : 41
             */

            private String image;
            private String name;
            private int categoryId;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }
        }
    }

    public static class AdvBannerListBean {
        /**
         * sec : 0
         * goodsId : 419
         * place : 2
         * rushId : 16
         * tagUrl : https://api.bscvip.com/detailv2/detailshare.html?goodsId=419&share=bc3707391851024f
         * type : 6
         * url : http://image.bscvip.com/banner_images/1557111833008037.png
         * appVer :
         */

        private int sec;
        private int goodsId;
        private int place;
        private int rushId;
        private String tagUrl;
        private int type;
        private String url;
        private String appVer;

        public int getSec() {
            return sec;
        }

        public void setSec(int sec) {
            this.sec = sec;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getPlace() {
            return place;
        }

        public void setPlace(int place) {
            this.place = place;
        }

        public int getRushId() {
            return rushId;
        }

        public void setRushId(int rushId) {
            this.rushId = rushId;
        }

        public String getTagUrl() {
            return tagUrl;
        }

        public void setTagUrl(String tagUrl) {
            this.tagUrl = tagUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAppVer() {
            return appVer;
        }

        public void setAppVer(String appVer) {
            this.appVer = appVer;
        }
    }

    public static class GoodsCategoryListBean {
        /**
         * titleImage : http://image.bscvip.com/category_images/1557126036340836.png
         * image : http://image.bscvip.com/category_images/1557124873204656.png
         * goodsList : [{"image":"http://image.bscvip.com/goods_images/1550483079035433.png","compareUrl":"","goodsId":289,"transport":0,"title":"脑黄金含量≈2倍深海鱼油的牡丹油","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1550483097320712.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097449016.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097592525.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097723281.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483097886318.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098026257.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098190640.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098330264.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098467249.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098616903.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098720319.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098886427.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483098988402.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099123402.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099253331.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099393827.png\" /><img src=\"http://image.bscvip.com/goods_images/1550483099581674.png\" />","favoriteNum":8,"price":278,"supplier":"","name":"牡之元 牡丹籽油","vipPrice":91.8,"wantNum":0,"sortBy":4,"categoryId":41,"sellNum":773,"freeShopping":80},{"image":"http://image.bscvip.com/goods_images/1550486241426735.png","compareUrl":"","goodsId":290,"transport":0,"title":"来自汉中的牡丹籽油","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1551765029263130.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029425547.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029607348.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029766617.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765029979041.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765030541138.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765030783889.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765031194836.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765031340299.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765031898580.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765032082445.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765032471455.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765032986883.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765033197857.png\" /><img src=\"http://image.bscvip.com/goods_images/1551765033398797.png\" />","favoriteNum":1,"price":628,"supplier":"","name":"牡之元 牡丹籽油","vipPrice":218,"wantNum":78,"sortBy":0,"categoryId":41,"sellNum":99,"freeShopping":80}]
         * name : 热门
         * categoryId : 1
         */

        private String titleImage;
        private String image;
        private String name;
        private int categoryId;
        private List<GoodsListBeanX> goodsList;

        public String getTitleImage() {
            return titleImage;
        }

        public void setTitleImage(String titleImage) {
            this.titleImage = titleImage;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public List<GoodsListBeanX> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBeanX> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBeanX {
            /**
             * image : http://image.bscvip.com/goods_images/1550483079035433.png
             * compareUrl :
             * goodsId : 289
             * transport : 0
             * title : 脑黄金含量≈2倍深海鱼油的牡丹油
             * cartNum : 0
             * content : <img src="http://image.bscvip.com/goods_images/1550483097320712.png" /><img src="http://image.bscvip.com/goods_images/1550483097449016.png" /><img src="http://image.bscvip.com/goods_images/1550483097592525.png" /><img src="http://image.bscvip.com/goods_images/1550483097723281.png" /><img src="http://image.bscvip.com/goods_images/1550483097886318.png" /><img src="http://image.bscvip.com/goods_images/1550483098026257.png" /><img src="http://image.bscvip.com/goods_images/1550483098190640.png" /><img src="http://image.bscvip.com/goods_images/1550483098330264.png" /><img src="http://image.bscvip.com/goods_images/1550483098467249.png" /><img src="http://image.bscvip.com/goods_images/1550483098616903.png" /><img src="http://image.bscvip.com/goods_images/1550483098720319.png" /><img src="http://image.bscvip.com/goods_images/1550483098886427.png" /><img src="http://image.bscvip.com/goods_images/1550483098988402.png" /><img src="http://image.bscvip.com/goods_images/1550483099123402.png" /><img src="http://image.bscvip.com/goods_images/1550483099253331.png" /><img src="http://image.bscvip.com/goods_images/1550483099393827.png" /><img src="http://image.bscvip.com/goods_images/1550483099581674.png" />
             * favoriteNum : 8
             * price : 278
             * supplier :
             * name : 牡之元 牡丹籽油
             * vipPrice : 91.8
             * wantNum : 0
             * sortBy : 4
             * categoryId : 41
             * sellNum : 773
             * freeShopping : 80
             */

            private String image;
            private String compareUrl;
            private int goodsId;
            private int transport;
            private String title;
            private int cartNum;
            private String content;
            private int favoriteNum;
            private String price;
            private String supplier;
            private String name;
            private String vipPrice;
            private int wantNum;
            private int sortBy;
            private int categoryId;
            private int sellNum;
            private int freeShopping;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getCompareUrl() {
                return compareUrl;
            }

            public void setCompareUrl(String compareUrl) {
                this.compareUrl = compareUrl;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getTransport() {
                return transport;
            }

            public void setTransport(int transport) {
                this.transport = transport;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCartNum() {
                return cartNum;
            }

            public void setCartNum(int cartNum) {
                this.cartNum = cartNum;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getFavoriteNum() {
                return favoriteNum;
            }

            public void setFavoriteNum(int favoriteNum) {
                this.favoriteNum = favoriteNum;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSupplier() {
                return supplier;
            }

            public void setSupplier(String supplier) {
                this.supplier = supplier;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVipPrice() {
                return vipPrice;
            }

            public void setVipPrice(String vipPrice) {
                this.vipPrice = vipPrice;
            }

            public int getWantNum() {
                return wantNum;
            }

            public void setWantNum(int wantNum) {
                this.wantNum = wantNum;
            }

            public int getSortBy() {
                return sortBy;
            }

            public void setSortBy(int sortBy) {
                this.sortBy = sortBy;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public int getSellNum() {
                return sellNum;
            }

            public void setSellNum(int sellNum) {
                this.sellNum = sellNum;
            }

            public int getFreeShopping() {
                return freeShopping;
            }

            public void setFreeShopping(int freeShopping) {
                this.freeShopping = freeShopping;
            }
        }
    }
}
