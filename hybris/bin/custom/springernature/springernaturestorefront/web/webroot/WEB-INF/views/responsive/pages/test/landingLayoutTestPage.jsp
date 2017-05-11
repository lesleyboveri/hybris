<%@ page import="de.hybris.platform.util.Config" %>

<style type="text/css">
    .buybox {
        margin: 16px 0 0;
        position: relative;
    }
    .buybox {
        font-family: Source Sans Pro, Helvetica, Arial, sans-serif;
        font-size: 14px;
        font-size: 1.4rem;
    }
    .buybox {
        zoom: 1;
    }
    .buybox:after,
    .buybox:before {
        content: '';
        display: table;
    }
    .buybox:after {
        clear: both;
    }
    /*---------------------------------*/
    .buybox .buybox__header {
        border: 1px solid #b3b3b3;
        border-bottom: 0;
        padding: 8px;
        position: relative;
        background-color: #f2f2f2;
    }
    .buybox__header .buybox__login {
        font-family: Source Sans Pro, Helvetica, Arial, sans-serif;
        font-size: 14px;
        font-size: 1.4rem;
        letter-spacing: .017em;
        display: inline-block;
        line-height: 1.2;
        padding: 0;
    }
    .buybox__header .buybox__login:before {
        position: absolute;
        top: 50%;
        -webkit-transform: perspective(1px) translateY(-50%);
        transform: perspective(1px) translateY(-50%);
        content: '\A';
        width: 34px;
        height: 34px;
        left: 10px;
    }
    /*---------------------------------*/
    .buybox .buybox__body {
        padding: 0;
        padding-bottom: 16px;
        position: relative;
        text-align: center;
        background-color: #fcfcfc;
        border: 1px solid #b3b3b3;
    }
    /******/
    .buybox__body .buybox__section {
        padding: 16px 12px 0 12px;
        text-align: left;
    }
    .buybox__section .buybox__buttons {
        text-align: center;
        width: 100%;
    }
    /********** mycopy buybox specific **********/
    .buybox.mycopy__buybox .buybox__body {
        background-color: transparent;
    }
    .buybox.mycopy__buybox .buybox__section .buybox__buttons {
        border-top: 0;
        padding-top: 0;
    }
    /******/
    .buybox__section:nth-child(2) .buybox__buttons {
        border-top: 1px solid #b3b3b3;
        padding-top: 20px;
    }
    .buybox__buttons .buybox__buy-button {
        display: inline-block;
        text-align: center;
        margin-bottom: 5px;
        padding: 6px 12px;
        max-width: 180px
    }
    .buybox__buttons .buybox__price {
        white-space: nowrap;
        text-align: center;
        font-size: larger;
        padding-top: 6px;
    }
    .buybox__section .buybox__meta {
        letter-spacing: 0;
        padding-top: 12px;
    }
    .buybox__section .buybox__meta:only-of-type {
        padding-top: 0;
        position: relative;
        bottom: 6px;
    }
    /********** mycopy buybox specific **********/
    .buybox.mycopy__buybox .buybox__section .buybox__meta {
        margin-top: 0;
        margin-bottom: 0;
    }
    /******/
    .buybox__meta .buybox__product-title {
        display: inline;
        font-weight: bold;
    }
    .buybox__meta .buybox__list {
        line-height: 1.3;
    }
    .buybox__meta .buybox__list li {
        position: relative;
        padding-left: 1em;
        list-style: none;
        margin-bottom: 5px;
    }
    .buybox__meta .buybox__list li:before {
        font-size: 1em;
        content: '\2022';
        float: left;
        position: relative;
        top: .1em;
        font-family: serif;
        font-weight: 600;
        text-align: center;
        line-height: inherit;
        color: #666;
        width: auto;
        margin-left: -1em;
    }
    .buybox__meta .buybox__list li:last-child {
        margin-bottom: 0;
    }
    /********** chapter buybox specific **********/
    .buybox.chapter__buybox .buybox__section .buybox__meta {
        border-top: 1px solid #d3d3d3;
        padding-top: 12px;
    }
    .buybox.chapter__buybox .buybox__section .buybox__buy-ebook {
        margin-bottom: 12px;
    }
    /******/
    /*---------------------------------*/
    .buybox .buybox__footer {
        border: 1px solid #b3b3b3;
        border-top: 0;
        padding: 8px;
        position: relative;
        border-style: dashed;
        line-height: 1.3;
    }
    /*-----------------------------------------------------------------*/
    @media screen and (min-width: 460px) and (max-width: 1074px) {
        .buybox__body .buybox__section {
            display: inline-block;
            vertical-align: top;
            padding: 12px 12px;
            padding-bottom: 0;
            text-align: left;
            width: 48%;
        }
        .buybox__body .buybox__section {
            padding-top: 16px;
            padding-left: 0;
        }
        .buybox__section:nth-of-type(2) .buybox__meta {
            border-left: 1px solid #d3d3d3;
            padding-left: 28px;
        }
        .buybox__section:nth-of-type(2) .buybox__buttons {
            border-top: 0;
            padding-top: 0;
            padding-left: 16px ;
        }
        .buybox__buttons .buybox__buy-button {
        }
        /********** article buybox specific **********/
        .buybox.article__buybox .buybox__section:nth-of-type(2) {
            margin-top: 16px;
            padding-top: 0;
        }
        .buybox.article__buybox .buybox__section:nth-of-type(2) .buybox__meta {
            margin-top: 40px;
            padding-top: 0;
            padding-bottom: 45px;
        }
        .buybox.article__buybox .buybox__section:nth-of-type(2) .buybox__meta:only-of-type {
            margin-top: 8px;
            padding-top: 12px;
            padding-bottom: 12px;
        }
        /********** mycopy buybox specific **********/
        .buybox.mycopy__buybox .buybox__section:first-child {
            width: 69%;
            padding-left: 12px ;
        }
        .buybox.mycopy__buybox .buybox__section:last-child {
            width: 29%;
        }
        /********** chapter buybox specific **********/
        .buybox.chapter__buybox .buybox__body {
            display: flex;
            align-items: stretch;
            padding-left: 12px;
            padding-top: 12px;
        }
        .buybox.chapter__buybox .buybox__section .buybox__meta {
            display: flex;
            align-items: center;
            height: 100%;
            border-top: 0;
            padding-top: 0;
        }
        /******/
    }
    /*-----------------------------------------------------------------*/
    @media screen and (max-width: 459px) {
        /********** mycopy buybox specific **********/
        .buybox.mycopy__buybox .buybox__body {
            padding-bottom: 5px;
        }
        .buybox.mycopy__buybox .buybox__section:last-child {
            text-align: center;
            width: 100%;
        }
        .buybox.mycopy__buybox .buybox__buttons {
            display: inline-block;
            width: 150px ;
        }
        /******/
    }
    /*-----------------------------------------------------------------*/
</style>

<div class="buybox article__buybox" data-webtrekkTrackId="532695141032829" data-webtrekkContentId="SL-article">
    <div class="buybox__header">
        <span class="buybox__login">Log in to check access</span>
    </div>
    <div class="buybox__body">
        <div class="buybox__section">
            <div class="buybox__buttons">
                <form class="buybox__form" action="<%=Config.getString("website.springerlink.https",
        "https://springerlink.local:9002/springernaturestorefront") + "/springerlink/en/slcart/add" %>" method="POST">

                    <input type="hidden" name="productCodePost" value="PPVJ"/>

                    <input type="hidden" name="type" value="article"/>

                    <input type="hidden" name="doi" value="10.1007/s10614-016-9582-3"/>

                    <input type="hidden" name="isxn" value="1572-9974"/>

                    <input type="hidden" name="contenttitle" value="Simple Agents, Intelligent Markets"/>

                    <input type="hidden" name="copyrightyear" value="2016"/>

                    <input type="hidden" name="year" value="2016"/>

                    <input type="hidden" name="authors" value="Karim Jamal, Michael Maier, Shyam Sunder"/>

                    <input type="hidden" name="title" value="Computational Economics"/>

                    <input type="hidden" name="mac" value="ba15ace17d072e29d830d274893303de"/>

                    <input type="hidden" name="returnurl" value="http://link.springer.com/article/10.1007/s10614-016-9582-3"/>

                    <button
                            class="buybox__buy-button c-button c-button--blue"
                            data-product-id="10.1007/s10614-016-9582-3_Simple Agents, Intelligent Markets"
                            data-tracking-category="ppv"
                            data-eCommerceParam_11="article-page"
                            data-gtm="buybox__buy-button">
                        <span class="buybox__buy" data-gtm="buybox__buy-button">Buy (PDF)</span>
                    </button>
                    <div class="buybox__price" data-gtm="buybox__buy-button">
                        USD&nbsp;39.95
                    </div>
                </form>
            </div>

        </div>
        <div class="buybox__section">

            <div class="buybox__meta">
                <ul class="buybox__list">
                    <li>Unlimited access to the full article</li>
                    <li>Instant download</li>
                    <li>Include local sales tax if applicable</li>
                </ul>
            </div>
        </div>

    </div>
    <div class="buybox__footer">
        <a class="js-buybox__inst-sub-link" data-gtm="buybox__inst-sub-link" href="http://www.springer.com/gp/shop/subscriptions?wt_mc=ThirdParty.SpringerLink.3.EPR653.ArticlePage_Institutional-customers">
            Learn about institutional subscriptions</a>
    </div>
</div>

<script>
    (function () {
        var forEach = function (array, callback, scope) {
            for (var i = 0; i < array.length; i++) {
                callback.call(scope, i, array[i]);
            }
        };

        var trackDomain = 'springergmbh01.webtrekk.net';
        var domain = 'link.springer.com';
        var loadEventSent = false;

        var buyboxRoot = document.getElementsByClassName('buybox')[0];
        var webtrekkTrackId = buyboxRoot.getAttribute('data-webtrekkTrackId');
        var webtrekkContentId = buyboxRoot.getAttribute('data-webtrekkContentId');

        function SubscriptionTrack(webtrekkTrackId) {
            if (!webtrekkTrackId) return;

            var data = {
                trackDomain: trackDomain,
                trackId: webtrekkTrackId,
                domain: domain,
                contentId: 'springer_com.buybox',
                customSessionParameter: {
                    2: getBusinessPartnerIds()
                }
            };

            if (!window.webtrekkV3 && console) {
                console.log('No webtrekk found.', data);
                return;
            }

            return new webtrekkV3(data);
        }

        function Track(product, webtrekkTrackId, eCommerceParam_11, action) {
            if (!product) return;

            var data = {
                trackDomain: trackDomain,
                trackId: webtrekkTrackId,
                domain: domain,
                contentId: webtrekkContentId,
                product: product.id,
                productStatus: action,
                productCategory: {
                    1: product.trackingCategory
                },
                customEcommerceParameter: {
                    9: domain,
                    11: eCommerceParam_11
                },
                customSessionParameter: {
                    2: getBusinessPartnerIds()
                }
            };

            if (!window.webtrekkV3 && console) {
                console.log('No webtrekk found.', data);
                return;
            }

            return new webtrekkV3(data);
        }

        function trackAddToCart(trackingConfig) {
            if (!trackingConfig) return;
            trackingConfig.sendinfo();
        }

        function trackOnLoad(trackingConfig) {
            if (loadEventSent) return;
            loadEventSent = true;
            if (!trackingConfig) return;
            trackingConfig.sendinfo();
        }

        function trackSubscription(trackingConfig) {
            if (!trackingConfig) return;
            trackingConfig.sendinfo({linkId: 'inst. subscription info'});
        }

        function getBusinessPartnerIds() {
            var businessPartnerIDs = [];
            try {
                var ids = window.dataLayer[0]["user"]["license"]["businessPartnerID"];
                if(ids && ids.length) businessPartnerIDs = ids;
            } catch(e) {}
            return businessPartnerIDs.join(";");
        }

        var bindClickLinkHandle = function (link) {
            if (!link) return;

            link.addEventListener('click', function (event) {
                trackSubscription(SubscriptionTrack(webtrekkTrackId));
            });
        };

        var bindLoadButtonHandle = function (button) {
            if (!button) return;

            var productId = button.getAttribute('data-product-id');
            var trackingCategory = button.getAttribute('data-tracking-category');
            var eCommerceParam_11 = button.getAttribute('data-eCommerceParam_11');

            window.addEventListener('load', function (event) {
                trackOnLoad(Track({
                    id: productId,
                    trackingCategory: trackingCategory
                }, webtrekkTrackId, eCommerceParam_11, 'view'));
            });
        };

        var bindClickButtonHandle = function (button) {
            if (!button) return;

            var productId = button.getAttribute('data-product-id');
            var trackingCategory = button.getAttribute('data-tracking-category');
            var eCommerceParam_11 = button.getAttribute('data-eCommerceParam_11');

            button.addEventListener('click', function (event) {
                trackAddToCart(Track({
                    id: productId,
                    trackingCategory: trackingCategory
                }, webtrekkTrackId, eCommerceParam_11, 'add'));
            });
        };

        var buyButtons = document.getElementsByClassName('buybox__buy-button');
        forEach(buyButtons, function (index, button) {
            bindLoadButtonHandle(button);
            bindClickButtonHandle(button);
        });

        var sublinks = document.getElementsByClassName('js-buybox__inst-sub-link');
        forEach(sublinks, function (index, link) {
            bindClickLinkHandle(link);
        });
    })();
</script>
