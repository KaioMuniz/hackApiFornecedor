package br.hackathon.com.application.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.hackathon.com.adapters.dtos.CotacaoMessage;
import br.hackathon.com.adapters.out.CotacaoRepository;
import br.hackathon.com.adapters.out.UsuarioRepository;
import br.hackathon.com.domain.models.Fornecedor;

@Component
public class RabbitMQConsumerComponent {

	@Autowired ObjectMapper objMapper;
	@Autowired MailSenderComponent mailSender;
	@Autowired CotacaoRepository cotacaoRepository;
	@Autowired UsuarioRepository usuarioRepository;
	
	@RabbitListener(queues = "cotacoes_encerradas")
	public void sendEmailCotacaoEncerrada(@Payload String message) {
		
		try {
			
			var notificacao = objMapper.readValue(message, CotacaoMessage.class);
			
			sendEmailEmpresa(notificacao.getEmailEmpresa());
			sendEmailFornecedorVencedor(notificacao.getEmailFornecedor());
			
			var cotacao = cotacaoRepository.findById(notificacao.getIdCotacao()).orElseThrow(() -> new RuntimeException("Nao achei kk"));
			
			var listFornecedores = cotacao.getFornecedores();
			
			for(Fornecedor fornecedor : listFornecedores) {
				
				var user = usuarioRepository.
						findById(fornecedor.getUsuarioId())
						.orElseThrow(() -> new RuntimeException("Usuário não existe"));
				
				sendEmailEmpresa(user.getEmail());
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendEmailFornecedorVencedor(String email) {
		var to = email;
		var subject = "COTAÇÃO ENCERRADA";
		var body = """
				<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 8px;
            overflow: hidden;
            border: 1px solid #dddddd;
        }
        .header {
            background-color: #e74c3c; /* Cor de alerta/encerrado */
            color: #ffffff;
            padding: 20px;
            text-align: center;
        }
        .content {
            padding: 30px;
            line-height: 1.6;
            color: #333333;
        }
        .footer {
            background-color: #f9f9f9;
            color: #777777;
            padding: 15px;
            text-align: center;
            font-size: 12px;
        }
        .status-badge {
            display: inline-block;
            background-color: #eeeeee;
            color: #666666;
            padding: 5px 15px;
            border-radius: 20px;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 style="margin: 0;">Cotação Encerrada</h1>
        </div>
        <div class="content">
            <p>Olá,</p>
            <p>Informamos que você foi o vencedor da COTAÇÃO concluída e não está mais aceitando novos lances ou propostas.</p>
            
            <div style="text-align: center;">
                <span class="status-badge">STATUS: ENCERRADA</span>
            </div>

            <p>Agradecemos a sua participação. Caso tenha dúvidas sobre o resultado ou os próximos passos, entre em contato com a nossa equipe de compras.</p>
            
            <p>Atenciosamente,<br>
            <strong>Equipe de Suprimentos</strong></p>
        </div>
        <div class="footer">
            Este é um e-mail automático, por favor não responda.
        </div>
    </div>
</body>
</html>
				""";
		
		mailSender.send(to, subject, body, true);
	}
	
	public void sendEmailEmpresa(String email) {
		var to = email;
		var subject = "COTAÇÃO ENCERRADA";
		var body = """
				<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 8px;
            overflow: hidden;
            border: 1px solid #dddddd;
        }
        .header {
            background-color: #e74c3c; /* Cor de alerta/encerrado */
            color: #ffffff;
            padding: 20px;
            text-align: center;
        }
        .content {
            padding: 30px;
            line-height: 1.6;
            color: #333333;
        }
        .footer {
            background-color: #f9f9f9;
            color: #777777;
            padding: 15px;
            text-align: center;
            font-size: 12px;
        }
        .status-badge {
            display: inline-block;
            background-color: #eeeeee;
            color: #666666;
            padding: 5px 15px;
            border-radius: 20px;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 style="margin: 0;">Cotação Encerrada</h1>
        </div>
        <div class="content">
            <p>Olá,</p>
            <p>Informamos que a <strong>Cotação %s</strong> foi concluída e não está mais aceitando novos lances ou propostas.</p>
            
            <div style="text-align: center;">
                <span class="status-badge">STATUS: ENCERRADA</span>
            </div>

            <p>Agradecemos a sua participação. Caso tenha dúvidas sobre o resultado ou os próximos passos, entre em contato com a nossa equipe de compras.</p>
            
            <p>Atenciosamente,<br>
            <strong>Equipe de Suprimentos</strong></p>
        </div>
        <div class="footer">
            Este é um e-mail automático, por favor não responda.
        </div>
    </div>
</body>
</html>
				""";
		
		mailSender.send(to, subject, body, true);
	}
	
	
}
