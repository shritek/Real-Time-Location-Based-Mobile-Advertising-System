
<!DOCTYPE html>
<?php session_start(); 
  unset($_SESSION['rad']);

	$_SESSION['rad']="NULL";
?>

<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
               
            });
            function sendPushNotification(id, d){
                var data = $('form#'+id).serialize();

                $('form#'+id).unbind('submit');                
                $.ajax({
                    url: "send_message.php",
                    type: 'GET',
                    data: data,
                    beforeSend: function() {
                        
                    },
                    success: function(data, textStatus, xhr) {
                          $('.txt_message').val("");
                    },
                    error: function(xhr, textStatus, errorThrown) {
                        
                    }
                });
                return false;
            }

	function getr()
	{
		document.getElementById("m").value=document.getElementById("ddl").value;
	}

        </script>
        <style type="text/css">
            .container{
                width: 950px;
                margin: 0 auto;
                padding: 0;
            }
            h1{
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                font-size: 24px;
                color: #777;
            }
            div.clear{
                clear: both;
            }
            ul.devices{
                margin: 0;
                padding: 0;
            }
            ul.devices li{
                float: left;
                list-style: none;
                border: 1px solid #dedede;
                padding: 10px;
                margin: 0 15px 25px 0;
                border-radius: 3px;
                -webkit-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.35);
                -moz-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.35);
                box-shadow: 0 1px 5px rgba(0, 0, 0, 0.35);
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                color: #555;
            }
            ul.devices li label, ul.devices li span{
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                font-size: 12px;
                font-style: normal;
                font-variant: normal;
                font-weight: bold;
                color: #393939;
                display: block;
                float: left;
            }
            ul.devices li label{
                height: 25px;
                width: 50px;                
            }
            ul.devices li textarea{
                float: left;
                resize: none;
            }
            ul.devices li .send_btn{
                background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#0096FF), to(#005DFF));
                background: -webkit-linear-gradient(0% 0%, 0% 100%, from(#0096FF), to(#005DFF));
                background: -moz-linear-gradient(center top, #0096FF, #005DFF);
                background: linear-gradient(#0096FF, #005DFF);
                text-shadow: 0 1px 0 rgba(0, 0, 0, 0.3);
                border-radius: 3px;
                color: #fff;
            }
        </style>
    </head>
    <body>
	<?php $count=0; ?>
        <div class="container">
            <hr/>
            <ul class="devices">
                        <li>
                            <form id="form" name=""  method="post" onsubmit="return sendPushNotification('form','ddl')"  >
                                <div class="clear"></div>
                                <div class="clear"></div>
                                <div class="send_container">                                
		<textarea rows="5" name="message" cols="25" class="txt_message" placeholder="Type message here"></textarea>
		<br><br>
		<p>Select Radius
		<select required name="ddl" id="ddl">
			<option value="1"  >1.0</option>
	  		<option value="1.5"  >1.5</option>
  			<option value="2" >2.0</option>
		</select>
		kms.  <br><br>
		
		<input type="hidden" name="regId" value="0"/>
		<input name="rip" type="hidden"  value="0"/>
                                    <input type="submit" class="send_btn" value="Send" onclick=""/>


                                </div>
                            </form>
		
		
                        </li>
            </ul>
        </div>
    </body>
</html>
