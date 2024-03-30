CURRENT=${PWD}

cd ${CURRENT}/k8s && kubectl apply -f  namespace.yml
cd ${CURRENT}/k8s && kubectl apply -f  loki-dns.yml
cd ${CURRENT}/k8s/math-add-subtract && kubectl apply -f .
cd ${CURRENT}/k8s/math-division-multiplication && kubectl apply -f .
