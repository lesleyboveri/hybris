FROM y.i.base
MAINTAINER axel.grossmann@hybris.de

RUN export DEBIAN_FRONTEND=noninteractive && \
  apt-get update && \
  apt-get -y install lsof

RUN mkdir -p /opt/solr
ENV SOLR=/opt/solr

ADD solrFromPlatform/solr/ ${SOLR}/

RUN chmod +x /opt/solr/bin/solr
  
EXPOSE 8983
WORKDIR ${SOLR}

CMD ["/bin/bash", "-c", "/opt/solr/bin/solr -f"]