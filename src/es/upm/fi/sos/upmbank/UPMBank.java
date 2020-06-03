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
		} catch(RemoteException e){
			System.out.println("RemoteException in changePassword");
			e.printStackTrace();
		}
	}

	private static void addBankAcc(double quantity){
		try{
			AddBankAcc bankAcc = new AddBankAcc();
			Deposit deposit = new Deposit();

			deposit.setQuantity(quantity);
			bankAcc.setArgs0(deposit);

			AddBankAccResponse response = stub.addBankAcc(bankAcc);

			if(response.is_returnSpecified() & response.get_return().isResultSpecified() & response.get_return().isIBANSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResult()){
					System.out.println("Cuenta creada");
					System.out.println("El IBAN de la cuenta es:"+response.get_return().getIBAN());
				}else{
					//Account wasn't create
					System.out.println("Error en la creacion de la cuenta");
				}
			}else{
				System.out.println("probleme en el formato de la respuesta");
			}

		} catch(RemoteException e){
			System.out.println("RemoteException in addBankAccount");
			e.printStackTrace();
		}
	}

	private static void closeBankAcc(String IBAN){
		try{
			CloseBankAcc bankAcc = new CloseBankAcc();
			BankAccount account = new BankAccount();

			account.setIBAN(IBAN);
			bankAcc.setArgs0(account);

			CloseBankAccResponse response = stub.closeBankAcc(bankAcc);

			if(response.is_returnSpecified() & response.get_return().isResponseSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResponse()){
					System.out.println("Cuenta borrada");
					System.out.println("La cuenta"+IBAN+" ha sido borrada: "+response.get_return().getResponse());
				}else{
					//Account wasn't create
					System.out.println("Error al borrar la cuenta");
				}
			}else{
				System.out.println("Problema en el formato de la respuesta");
			}
		} catch(RemoteException e){
			System.out.println("RemoteException in closeBankAcc");
			e.printStackTrace();
		}
	}

	private static void addIncome(String IBAN, double income){
		try{
			AddIncome addIncome = new AddIncome();
			Movement movement = new Movement();

			movement.setQuantity(income);
			addIncome.setArgs0(movement);

			AddIncomeResponse response = stub.addIncome(addIncome);

			if(response.is_returnSpecified() & response.get_return().isResultSpecified() & response.get_return().isBalanceSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResult()){
					System.out.println("Ingreso anadido");
					System.out.println("La cuenta"+IBAN+" tiene un balance de: "+response.get_return().getBalance());
				}else{
					//Account wasn't create
					System.out.println("Error al ingresar dinero a la cuenta");
				}
			}else{
				System.out.println("Problema en el formato de la respuesta");
			}
		} catch(RemoteException e){
			System.out.println("RemoteException in addIncome");
			e.printStackTrace();
		}
	}

	private static void addWithdraw(String IBAN, double withdraw){
		try{
			AddWithdrawal withdrawal = new AddWithdrawal();
			Movement movement = new Movement();

			movement.setQuantity(withdraw);
			withdrawal.setArgs0(movement);

			AddWithdrawalResponse response = stub.addWithdrawal(withdrawal);

			if(response.is_returnSpecified() & response.get_return().isResultSpecified() & response.get_return().isBalanceSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResult()){
					System.out.println("Retirada contabilizada");
					System.out.println("La cuenta"+IBAN+" tiene un balance de: "+response.get_return().getBalance());
				}else{
					//Account wasn't create
					System.out.println("Error al retirar dinero de la cuenta: "+IBAN);
				}
			}else{
				System.out.println("Problema en el formato de la respuesta");
			}
		} catch(RemoteException e){
			System.out.println("RemoteException in addWithdrawal");
			e.printStackTrace();
		}
	}

	public static void getMyMovement(){
		try{
			GetMyMovements movements = new GetMyMovements();


			GetMyMovementsResponse response = stub.getMyMovements(movements);

			if(response.is_returnSpecified() & response.get_return().isResultSpecified() & response.get_return().isMovementQuantitiesSpecified()){
				System.out.println("Response: "+response.toString()+"\n Object Response: "+response.get_return().toString());
				if(response.get_return().getResult()){
					System.out.println("Movimientos bancarios");
					double[] res = response.get_return().getMovementQuantities();
					int i = 1;
					System.out.println("1 siendo el mas antiguo y 10 el mas recien -> ");
					System.out.println(" --------------------- ");
					for(Double d: res){
						System.out.println("Numero "+i+": "+d);
						i++;
					}
				}else{
					//Account wasn't create
					System.out.println("Error al recuperar los movimientos");
				}
			}else{
				System.out.println("Problema en el formato de la respuesta");
			}
		} catch(RemoteException e){
			System.out.println("RemoteException in getMyMovement");
			e.printStackTrace();
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
				System.out.println("1: Añadir un usuario");
				System.out.println("2: Borrar usuario");
				System.out.println("3: Modificar contrasena");
				System.out.println("4: Añadir cuenta bancaria");
				System.out.println("5: Cerrar cuenta bancaria");
				System.out.println("6: Depositar dinero en la cuenta");
				System.out.println("7: Retirar dinero");
				System.out.println("8: Ultimos movimientos");
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
					case 4:
						System.out.println("Escriba la cantidad con la que quiere iniciar su cuenta");
						System.out.println("Ingreso inicial: ");
						if(user.hasNextDouble()) {
							double quantity = user.nextDouble();
							addBankAcc(quantity);
						}
						break;
					case 5:
						System.out.println("Indique la cuenta que quiere cerrar");
						System.out.println("IBAN: ");
						if(user.hasNext()){
							String IBAN = user.next();
							closeBankAcc(IBAN);
						}
						break;
					case 6:
						System.out.println("Indique a que cuenta quiere ingresar dinero");
						System.out.println("IBAN: ");
						String IBAN;
						if(user.hasNext()){
							IBAN = user.next();

							System.out.println("Indique la cantidad que quiere ingresar");
							System.out.println("Cantidad: ");
							double income;
							if(user.hasNextDouble()){
								income = user.nextDouble();
								addIncome(IBAN, income);
							}
						}
						break;
					case 7:
						System.out.println("Indique a que cuenta quiere retirar dinero");
						System.out.println("IBAN: ");
						String IBAN2;
						if(user.hasNext()){
							IBAN2 = user.next();

							System.out.println("Indique la cantidad que quiere retirar");
							System.out.println("Cantidad: ");
							double withdraw;
							if(user.hasNextDouble()){
								withdraw = user.nextDouble();
								addWithdraw(IBAN2, withdraw);
							}
						}
						break;
					case 8:
						getMyMovement();
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
