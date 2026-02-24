#!/bin/bash
set -euo pipefail
sudo dnf update -y
sudo dnf install -y java-11-amazon-corretto-headless
sudo sh -c 'id -u nexus >/dev/null 2>&1 || useradd --system --create-home --shell /sbin/nologin nexus'
NEXUS_VERSION="3.89.1-02"
NEXUS_TGZ="nexus-${NEXUS_VERSION}-linux-x86_64.tar.gz"
NEXUS_URL="https://download.sonatype.com/nexus/3/${NEXUS_TGZ}"
echo "=== Downloading Nexus ${NEXUS_VERSION} ==="
cd /opt
sudo curl -fL --retry 10 --retry-all-errors -o "${NEXUS_TGZ}" "${NEXUS_URL}"
echo "=== Extracting Nexus ==="
sudo tar -zxf "${NEXUS_TGZ}"
NEXUS_DIR=$(find /opt -maxdepth 1 -type d -name "nexus-${NEXUS_VERSION}*" | head -n 1)
sudo mv "${NEXUS_DIR}" /opt/nexus
echo "=== Creating work directory ==="
mkdir -p /opt/sonatype-work
echo "=== Fixing permissions ==="
sudo chown -R nexus:nexus /opt/nexus /opt/sonatype-work
sudo sed -i -e 's/^-Xms.*/-Xms512m/' -e 's/^-Xmx.*/-Xmx512m/' /opt/nexus/bin/nexus.vmoptions
echo 'run_as_user="nexus"' | sudo tee /opt/nexus/bin/nexus.rc >/dev/null
sudo chown nexus:nexus /opt/nexus/bin/nexus.rc
sudo chmod 644 /opt/nexus/bin/nexus.rc
sudo tee /etc/systemd/system/nexus.service >/dev/null <<'EOF'
[Unit]
Description=Sonatype Nexus Repository Manager
After=network.target

[Service]
Type=simple
User=nexus
Group=nexus
LimitNOFILE=65536
WorkingDirectory=/opt/nexus
ExecStart=/opt/nexus/bin/nexus run
Restart=on-failure
RestartSec=10
TimeoutStartSec=300

[Install]
WantedBy=multi-user.target
EOF
sudo systemctl daemon-reload
sudo systemctl enable nexus
sudo systemctl start nexus
sudo systemctl status nexus -l
echo "=== Nexus service started ==="
systemctl status nexus --no-pager