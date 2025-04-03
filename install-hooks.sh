#!/bin/sh
echo "Instalando Git hooks..."
cp -r .githooks/* .git/hooks/
chmod +x .git/hooks/*
echo "Git hooks instalados."
