package Coinbear;

import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.io.File; // Import the File class
import java.io.IOException;
import java.io.FileWriter;

public class CoinBear {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

	public static int difficulty = 5;
	public static float minimumTransaction = 0.1f;
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;
	public static long timeStamp;

	public static void main(String[] args) throws IOException {
		try {
			new GUI();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			File myObj = new File("ledger.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		FileWriter myWriter = new FileWriter("ledger.txt");

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // Setup Bouncey castle as a
																						// Security Provider

		// Create wallets:
		walletA = new Wallet();
		walletB = new Wallet();
		Wallet coinbase = new Wallet();
		timeStamp = new Date().getTime();

		// add our blocks to the blockchain ArrayList:
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 50f, null);
		genesisTransaction.generateSignature(coinbase.privateKey); // manually sign the genesis transaction
		genesisTransaction.transactionId = "0"; // manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value,
				genesisTransaction.transactionId)); // manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); // its important to store
																							// our first transaction in
																							// the UTXOs list

		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		// File Writing
		if (isChainValid() == true) {
			myWriter.write(genesis.hash);
			myWriter.write(" : " + blockchain);
			myWriter.write(" : " + timeStamp);
			myWriter.write("\n");
			System.out.println("Successfully wrote to the Ledger File.");
			System.out.println("\n");
		} else {
			myWriter.close();
			return;
		}
	

        Block block1 = new Block(genesis.hash);
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
        if(isChainValid() == true){
            myWriter.write(block1.hash);
            myWriter.write(" : " + blockchain);
            myWriter.write(" : " + timeStamp);
            myWriter.write("\n");
            System.out.println("Successfully wrote to the Ledger File.");
            System.out.println("\n");
            }else{
				myWriter.close();
                return;
        }

     

		   myWriter.close();
		   

		 

	}

	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("#Current Hashes not equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("#Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for(int t=0; t <currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);
				
				if(!currentTransaction.verifySignature()) {
					System.out.println("#Signature on Transaction(" + t + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
					return false; 
				}
				
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if(tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					
					if(input.UTXO.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
				
			}
			
		}
		System.out.println("Blockchain is valid");
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
    }
   
}

