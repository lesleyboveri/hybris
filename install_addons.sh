#!/bin/sh

# This script is a wrapper script for our required addon install commands.

ant addoninstall -Daddonnames="acceleratorwebservicesaddon" -DaddonStorefront.ycommercewebservices="ycommercewebservices"
ant addoninstall -Daddonnames="commerceorgsamplesaddon,orderselfserviceaddon,assistedservicestorefront,customerticketingaddon,promotionenginesamplesaddon,textfieldconfiguratortemplateaddon,liveeditaddon,smarteditaddon" -DaddonStorefront.yacceleratorstorefront="macmillanstorefront,springernaturestorefront"
ant addoninstall -Daddonnames="sapcoreaddon" -DaddonStorefront.yacceleratorstorefront="springernaturestorefront"
ant addoninstall -Daddonnames="worldpayaddon,worldpaynotificationaddon" -DaddonStorefront.yacceleratorstorefront="macmillanstorefront,springernaturestorefront"
