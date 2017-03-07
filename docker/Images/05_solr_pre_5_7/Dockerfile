FROM y.i.base
MAINTAINER axel.grossmann@hybris.de

RUN mkdir -p /opt/solr-standalone
ENV SOLR=/opt/solr-standalone

ADD solrFromPlatform/server/ ${SOLR}/
ADD patches/* ${SOLR}/

WORKDIR ${SOLR}
RUN chmod +x start.sh

EXPOSE 8983

WORKDIR ${SOLR}
CMD ["./start.sh"]
