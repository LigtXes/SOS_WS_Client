package es.upm.fi.sos.upmbank;

import java.rmi.RemoteException;
import java.util.Scanner;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.upmbank.UPMBankWSStub.*;


public class UPMBank {
	
	private static UPMBankWSStub stub;
	private static Login login;
	
	private static void login(String username, String password){
		try {
			stub = new UPMBankWSStub();//Cada operacion login creara un nuevo cliente
			login = new Login();
			
			stub._getServiceClient().getOptions().setManageSession(true);

			
			UPMBankWSStub.User user = new UPMBankWSStub.User();
			user.setName(username);
			user.setPwd(password);
			
			login.setArgs0(user);
			
			System.out.println("User: "+username+" Password: "+password);
			Boolean response = stub.login(login).get_return().getResponse();
			System.out.println("Response received");
			
			if(response){
				System.out.println("Connexion conseguida");
			}else{
				System.out.println("La conexion a fallado, intentalo de nuevo");
				login = null;
			}
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			System.out.println("Axis Error in Login");
			login = null;
			stub = null;
			e.printStackTrace();
		}  catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("RemoteExeption in Login");
			e.printStackTrace();
		}
	}

	private static void addUser(String name){
		try{
			UPMBankWSStub.AddUser user = new UPMBankWSStub.AddUser();
			Username username = new Username();
			
			username.setUsername(name);
			user.setArgs0(username);
			
			AddUserResponseE response = stub.addUser(user);
			System.out.println("Response: "+response.toString());
			
			if(response.get_return().getResponse()){
				//Add User works
				System.out.println("Creacion del nuevo User echa");
				System.out.println("Username: "+ user+"\nPassword: "+response.get_return().getPwd());
			}else{
				//Add User didn't work
				System.out.println("La creacion del user no funciono");
			}
		} catch (RemoteException e){
			System.out.println("RemoteExeption in AddUser");
			e.printStackTrace();
		}
		
	}
	
	private static void logout(){
		try{
			Logout exit = new Logout();
			stub.logout(exit);
			stub = null;
			login = null;
			System.out.println("Deconexion hecha");
		} catch(RemoteException e){
			System.out.println("RemoteExeption in Logout");
			e.printStackTrace();
			
		}
	}
	
	private static void removeUser(String username){
		try{
			RemoveUser removeUser = new RemoveUser();
			Username user = new Username();;
			
			user.setUsername(username);
			removeUser.setArgs0(user);
			
			RemoveUserResponse response = stub.removeUser(removeUser);
			
			if(response.is_returnSpecified() & response.get_return().isResponseSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResponse()){
					//User remove
					System.out.println("El usuario: "+username+" ha sido borrado");
				}else{
					//Remove don't work - user probably doesn't exist
					System.out.println("El usuario: "+username+" no ha sido borrado");

				}
			}else{
				System.out.println("Error de formato en la respuesta");
			}
		} catch(RemoteException e){
			System.out.println("RemoteException in removeUser");
			e.printStackTrace();
		}
	}
	
	private static void changePassword(String old, String newOne){
		try{
			ChangePassword pass = new ChangePassword();
			PasswordPair pair = new PasswordPair();
			
			pair.setOldpwd(old);
			pair.setNewpwd(newOne);
			pass.setArgs0(pair);
			
			ChangePasswordResponse response = stub.changePassword(pass);
			//Checking if all variable are create
			if(response.is_returnSpecified() & response.get_return().isResponseSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResponse()){
					//True
					System.out.println("La contrasena se ha cambiado con exito");
				}else{
					//False
					System.out.println("Error en el cambio de la contrasena");
				}
			}else{
				System.out.println("Problema en el formato de la respuesta");
			}
		} catch (RemoteException e){
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		login = null;
		
		while(true){
			if(login == null){
				System.out.println("Operaciones disponibles");
				System.out.println("1: Login");
				System.out.println("2: Exit");
				Scanner keyboard = new Scanner(System.in);
				int read = keyboard.nextInt();
				
				switch(read){ 
					case 1:
						System.out.println("Username: ");
						Scanner user = new Scanner(System.in);
						String username = user.next();
						
						System.out.println("Password: ");
						Scanner pass = new Scanner(System.in);
						String password = pass.next();
						
						login(username, password);
						
						break;
					case 2: 
						return;
					default: 
						System.out.println("No coresponde a ninguna operacion");	
				}

			}else{
				System.out.println("Operaciones disponibles");
				System.out.println("1: Anadir un usuario");
				System.out.println("2: Borrar usuario");
				System.out.println("3: Modificar contrasena");
				System.out.println("4: ");
				System.out.println("5: ");
				System.out.println("6: ");
				System.out.println("7: ");
				System.out.println("8: ");
				System.out.println("9: Desconexion");
				
				Scanner keyboard = new Scanner(System.in);
				int read = keyboard.nextInt();
				Scanner user = new Scanner(System.in);
				
				switch(read){
					case 1:
						System.out.println("Escriba el nombre del usuario a anadir");
						System.out.println("Username: ");
						String username = user.next();
						
						addUser(username);
						break;
					case 2:
						System.out.println("Escriba el nombre del usuario a borrar");
						System.out.println("Username: ");
						String username2 = user.next();
						
						removeUser(username2);

						break;
					case 3:
						System.out.println("Escriba la contrasena actual");
						System.out.println("Old Password: ");
						String oldPassword = user.next();

						System.out.println("Escriba la nueva contrasena");
						System.out.println("New Password: ");
						String newPassword = user.next();
						
						changePassword(oldPassword, newPassword);
						break;
					case 9: 
						logout();
						break;
					default: 
						System.out.println("No coresponde a ninguna operacion");
						break;
				}
			}
		}
	}

}
