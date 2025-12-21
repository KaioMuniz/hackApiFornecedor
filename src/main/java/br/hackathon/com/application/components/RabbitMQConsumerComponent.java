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
	
	@RabbitListener(queues = "convites_fornecedores")
	public void emailConviteFornecedor(@Payload String message) {
		try {
			var notificacao = objMapper.readValue(message, CotacaoMessage.class);
			
			emailConviteFornecedor(notificacao.getEmailFornecedor());
			confirmacaoEmailEnviadoParaFornecedor(notificacao.getEmailEmpresa());
		}
		catch(Exception ex) {
			ex.printStackTrace();
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
	
	public void sendEmailConviteFornecedor(String email) {
		var to = email;
		var subject = "Nova proposta!";
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
            background-color: #2c3e50; /* Azul Profissional */
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
        .button {
            display: inline-block;
            padding: 12px 25px;
            background-color: #27ae60; /* Verde de ação */
            color: #ffffff;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin-top: 20px;
        }
        .details {
            background-color: #f8f9fa;
            border-left: 4px solid #2c3e50;
            padding: 15px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 style="margin: 0; font-size: 22px;">Convite para Cotação</h1>
        </div>
        <div class="content">
            <p>Prezado Parceiro,</p>
            <p>Nossa empresa está iniciando um novo processo de compra e gostaríamos de convidar sua organização para enviar uma proposta comercial.</p>
            
            <div class="details">
                <strong>Processo:</strong> #98765 - Aquisição de Insumos<br>
                <strong>Prazo Limite:</strong> 30 de Outubro de 2023<br>
                <strong>Departamento:</strong> Compras Estratégicas
            </div>

            <p>Acreditamos que seus produtos/serviços estão alinhados com nossos padrões de qualidade e gostaríamos de avaliar sua oferta.</p>
            
            <div style="text-align: center;">
                <a href="#" class="button">Visualizar Detalhes e Responder</a>
            </div>

            <p style="margin-top: 30px;">Caso tenha alguma dúvida técnica, basta responder a este e-mail ou entrar em contato pelo portal do fornecedor.</p>
            
            <p>Atenciosamente,<br>
            <strong>Departamento de Suprimentos</strong></p>
        </div>
        <div class="footer">
            Este é um convite formal de cotação enviado via Sistema de Compras.
        </div>
    </div>
</body>
</html>
				""";
		
		mailSender.send(to, subject, body, true);
	}
	
	public void confirmacaoEmailEnviadoParaFornecedor(String email) {
		var to = email;
		var subject = "Convite enviado!";
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
            background-color: #8e44ad; /* Roxo: Notificação de Sistema */
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
        .info-box {
            background-color: #f0f7ff;
            border: 1px dashed #8e44ad;
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
        }
        .check-icon {
            color: #27ae60;
            font-size: 18px;
            margin-right: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 style="margin: 0; font-size: 20px;">Confirmação de Recebimento</h1>
        </div>
        <div class="content">
            <p>Olá, <strong>Equipe de Compras</strong>,</p>
            <p>Este é um aviso automático para informar que o convite de cotação enviado recentemente foi entregue com sucesso ao destinatário.</p>
            
            <div class="info-box">
                <p style="margin: 5px 0;"><span class="check-icon">✔</span> <strong>Fornecedor:</strong> Nome do Fornecedor Ltda</p>
                <p style="margin: 5px 0;"><span class="check-icon">✔</span> <strong>Cotação:</strong> #98765 - Insumos de Produção</p>
                <p style="margin: 5px 0;"><span class="check-icon">✔</span> <strong>Data/Hora:</strong> 21 de Dezembro de 2025 às 11:15</p>
            </div>

            <p>O fornecedor já possui acesso aos documentos e prazos. Você será notificado assim que uma proposta for submetida no portal.</p>
				""";
		
		mailSender.send(to, subject, body, true);
	}
	
}
