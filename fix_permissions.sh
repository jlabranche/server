find -iname *.class | sudo xargs -I{} chmod 775 {}
find -iname *.class | sudo xargs -I{} chown sslliveq:ssl-live {}
