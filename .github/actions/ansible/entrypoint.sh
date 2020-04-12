#!/bin/sh

echo "$VAULT_PASS" > ~/.vault_password.txt
mkdir ~/secret
ansible-vault view --vault-password-file ~/.vault_password.txt .ansible/ssh.txt > ~/secret/id_rsa
chmod 0600 ~/secret/id_rsa
ansible-playbook --vault-password-file ~/.vault_password.txt -i hosts .ansible/deploy.yml