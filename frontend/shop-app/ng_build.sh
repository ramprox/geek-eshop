ng build
rm -r ${HOME}/eshop/nginx/shared/*
cp -r dist/shop-app/* ${HOME}/eshop/nginx/shared/
