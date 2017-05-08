#!/bin/sh
(
cd hybris/bin/platform
# This script is a wrapper script for our required addon install commands.
ant addoninstall -Daddonnames="acceleratorwebservicesaddon" -DaddonStorefront.ycommercewebservices="ycommercewebservices"

ant addoninstall -Daddonnames="commerceorgsamplesaddon,orderselfserviceaddon,assistedservicestorefront,springernatureassistedservicestorefront,customerticketingaddon,promotionenginesamplesaddon,textfieldconfiguratortemplateaddon,liveeditaddon,smarteditaddon" -DaddonStorefront.yacceleratorstorefront="macmillanstorefront,springernaturestorefront"
ant addoninstall -Daddonnames="sapcoreaddon,springerlinkcheckoutaddon" -DaddonStorefront.yacceleratorstorefront="springernaturestorefront"
ant addoninstall -Daddonnames="worldpayaddon,springernatureworldpayaddon,worldpaynotificationaddon" -DaddonStorefront.yacceleratorstorefront="macmillanstorefront,springernaturestorefront"
)
