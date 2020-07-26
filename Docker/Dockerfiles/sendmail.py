from email.mime.text import MIMEText
from email.utils import formatdate
import smtplib

smtp = smtplib.SMTP('host.docker.internal', 9205)
smtp.ehlo()

from_addr = "test-from@test.com"
to_addr = "keikotani926@gmail.com"

msg = MIMEText("Hello Postfix.")
msg['Subject'] = "メール送信テスト"
msg['From'] = from_addr
msg['To'] = to_addr
msg['Date'] = formatdate()

print(msg)

smtp.sendmail(from_addr, to_addr, msg.as_string())
smtp.close()