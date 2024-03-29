
/**
 * UPMBankWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package es.upm.fi.sos.upmbank;

    /**
     *  UPMBankWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class UPMBankWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public UPMBankWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public UPMBankWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addBankAcc method
            * override this method for handling normal response from addBankAcc operation
            */
           public void receiveResultaddBankAcc(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.AddBankAccResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addBankAcc operation
           */
            public void receiveErroraddBankAcc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for closeBankAcc method
            * override this method for handling normal response from closeBankAcc operation
            */
           public void receiveResultcloseBankAcc(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.CloseBankAccResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from closeBankAcc operation
           */
            public void receiveErrorcloseBankAcc(java.lang.Exception e) {
            }
                
               // No methods generated for meps other than in-out
                
           /**
            * auto generated Axis2 call back method for removeUser method
            * override this method for handling normal response from removeUser operation
            */
           public void receiveResultremoveUser(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.RemoveUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeUser operation
           */
            public void receiveErrorremoveUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addWithdrawal method
            * override this method for handling normal response from addWithdrawal operation
            */
           public void receiveResultaddWithdrawal(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.AddWithdrawalResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addWithdrawal operation
           */
            public void receiveErroraddWithdrawal(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addUser method
            * override this method for handling normal response from addUser operation
            */
           public void receiveResultaddUser(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.AddUserResponseE result
                        ) {
        	   
        	  
        	   es.upm.fi.sos.upmbank.UPMBankWSStub.AddUserResponseE response = result;
   			System.out.println("Response: "+response.toString());
   			
   			if(response.get_return().getResponse()){
   				//Add User works
   				System.out.println("Creacion del nuevo User echa");
   				System.out.println("Username: "+ UPMBank.login.getArgs0().localName+"\nPassword: "+response.get_return().getPwd());
   			}else{
   				//Add User didn't work
   				System.out.println("La creacion del user no funciono");
   			}
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addUser operation
           */
            public void receiveErroraddUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addIncome method
            * override this method for handling normal response from addIncome operation
            */
           public void receiveResultaddIncome(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.AddIncomeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addIncome operation
           */
            public void receiveErroraddIncome(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.LoginResponse result
                        ) {
        	   Boolean response = result.get_return().getResponse();
   			System.out.println("Response received");
   			
   			if(response){
   				System.out.println("Connexion conseguida");
   			}else{
   				System.out.println("La conexion a fallado, intentalo de nuevo");
   				UPMBank.login = null;
   			}
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getMyMovements method
            * override this method for handling normal response from getMyMovements operation
            */
           public void receiveResultgetMyMovements(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.GetMyMovementsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getMyMovements operation
           */
            public void receiveErrorgetMyMovements(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for changePassword method
            * override this method for handling normal response from changePassword operation
            */
           public void receiveResultchangePassword(
                    es.upm.fi.sos.upmbank.UPMBankWSStub.ChangePasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changePassword operation
           */
            public void receiveErrorchangePassword(java.lang.Exception e) {
            }
                


    }
    