package pl.wsb.fitnesstracker.mail.internal;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;

import java.util.List;

/**
 * EmailSender implementation using Mailtrap Java SDK (REST API with token).
 */
@Component
@RequiredArgsConstructor
class EmailSenderImpl implements EmailSender {

    @Value("${api.key}")
    private final String token;

    private MailtrapClient client() {
        return MailtrapClientFactory.createMailtrapClient(
                new MailtrapConfig.Builder()
                        .token(token)
                        .build()
        );
    }

    @Override
    public void send(EmailDto email) {
        MailtrapMail mail = MailtrapMail.builder()
                .from(new Address("hello@demomailtrap.co", "FitnessTracker WSB"))
                .to(List.of(new Address("p.maliszewski00@gmail.com")))
                .subject(email.subject())
                .text(email.content())
                .category("Weekly Report")
                .build();

        try {
            System.out.println(client().send(mail));
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
