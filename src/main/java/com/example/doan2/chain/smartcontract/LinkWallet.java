package com.example.doan2;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class LinkWallet extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061002d61002261003260201b60201c565b61003a60201b60201c565b6100fe565b600033905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b610ed98061010d6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806303fb0114146100675780635cfff11714610097578063715018a6146100c75780638da5cb5b146100d1578063d729e284146100ef578063f2fde38b1461010b575b600080fd5b610081600480360381019061007c919061080a565b610127565b60405161008e91906108d0565b60405180910390f35b6100b160048036038101906100ac9190610a27565b6101f8565b6040516100be9190610a7f565b60405180910390f35b6100cf610240565b005b6100d9610254565b6040516100e69190610a7f565b60405180910390f35b61010960048036038101906101049190610a9a565b61027d565b005b6101256004803603810190610120919061080a565b610528565b005b6060600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020805461017390610b25565b80601f016020809104026020016040519081016040528092919081815260200182805461019f90610b25565b80156101ec5780601f106101c1576101008083540402835291602001916101ec565b820191906000526020600020905b8154815290600101906020018083116101cf57829003601f168201915b50505050509050919050565b600060028260405161020a9190610b92565b908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6102486105ab565b6102526000610629565b565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6102856105ab565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036102f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102eb90610bf5565b60405180910390fd5b6000815111610338576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161032f90610c61565b60405180910390fd5b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020805461038490610b25565b9050146103c6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103bd90610cf3565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff166002826040516103ee9190610b92565b908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610473576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161046a90610d85565b60405180910390fd5b80600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090805190602001906104c69291906106f5565b50816002826040516104d89190610b92565b908152602001604051809103902060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b6105306105ab565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361059f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161059690610e17565b60405180910390fd5b6105a881610629565b50565b6105b36106ed565b73ffffffffffffffffffffffffffffffffffffffff166105d1610254565b73ffffffffffffffffffffffffffffffffffffffff1614610627576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161061e90610e83565b60405180910390fd5b565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600033905090565b82805461070190610b25565b90600052602060002090601f016020900481019282610723576000855561076a565b82601f1061073c57805160ff191683800117855561076a565b8280016001018555821561076a579182015b8281111561076957825182559160200191906001019061074e565b5b509050610777919061077b565b5090565b5b8082111561079457600081600090555060010161077c565b5090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006107d7826107ac565b9050919050565b6107e7816107cc565b81146107f257600080fd5b50565b600081359050610804816107de565b92915050565b6000602082840312156108205761081f6107a2565b5b600061082e848285016107f5565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610871578082015181840152602081019050610856565b83811115610880576000848401525b50505050565b6000601f19601f8301169050919050565b60006108a282610837565b6108ac8185610842565b93506108bc818560208601610853565b6108c581610886565b840191505092915050565b600060208201905081810360008301526108ea8184610897565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61093482610886565b810181811067ffffffffffffffff82111715610953576109526108fc565b5b80604052505050565b6000610966610798565b9050610972828261092b565b919050565b600067ffffffffffffffff821115610992576109916108fc565b5b61099b82610886565b9050602081019050919050565b82818337600083830152505050565b60006109ca6109c584610977565b61095c565b9050828152602081018484840111156109e6576109e56108f7565b5b6109f18482856109a8565b509392505050565b600082601f830112610a0e57610a0d6108f2565b5b8135610a1e8482602086016109b7565b91505092915050565b600060208284031215610a3d57610a3c6107a2565b5b600082013567ffffffffffffffff811115610a5b57610a5a6107a7565b5b610a67848285016109f9565b91505092915050565b610a79816107cc565b82525050565b6000602082019050610a946000830184610a70565b92915050565b60008060408385031215610ab157610ab06107a2565b5b6000610abf858286016107f5565b925050602083013567ffffffffffffffff811115610ae057610adf6107a7565b5b610aec858286016109f9565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610b3d57607f821691505b602082108103610b5057610b4f610af6565b5b50919050565b600081905092915050565b6000610b6c82610837565b610b768185610b56565b9350610b86818560208601610853565b80840191505092915050565b6000610b9e8284610b61565b915081905092915050565b7f57616c6c657420416464726573732073686f756c642062652076616c69640000600082015250565b6000610bdf601e83610842565b9150610bea82610ba9565b602082019050919050565b60006020820190508181036000830152610c0e81610bd2565b9050919050565b7f5573657249642073686f756c64206e6f7420656d707479000000000000000000600082015250565b6000610c4b601783610842565b9150610c5682610c15565b602082019050919050565b60006020820190508181036000830152610c7a81610c3e565b9050919050565b7f57616c6c6574204164647265737320697320616c7265616479206c696e6b656460008201527f20746f20616e6f74686572205573657249640000000000000000000000000000602082015250565b6000610cdd603283610842565b9150610ce882610c81565b604082019050919050565b60006020820190508181036000830152610d0c81610cd0565b9050919050565b7f55736572496420697320616c7265616479206c696e6b656420746f20616e6f7460008201527f6865722057616c6c657420416464726573730000000000000000000000000000602082015250565b6000610d6f603283610842565b9150610d7a82610d13565b604082019050919050565b60006020820190508181036000830152610d9e81610d62565b9050919050565b7f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160008201527f6464726573730000000000000000000000000000000000000000000000000000602082015250565b6000610e01602683610842565b9150610e0c82610da5565b604082019050919050565b60006020820190508181036000830152610e3081610df4565b9050919050565b7f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572600082015250565b6000610e6d602083610842565b9150610e7882610e37565b602082019050919050565b60006020820190508181036000830152610e9c81610e60565b905091905056fea2646970667358221220180e6c0c0ae6e56e02442df4fbc4499fc7ca8b0d232e6ae754c85af1a3833cfe64736f6c634300080d0033";

    public static final String FUNC_GETUSERIDBYWALLETADDRESS = "getUserIdByWalletAddress";

    public static final String FUNC_GETWALLETADDRESSBYUSERID = "getWalletAddressByUserId";

    public static final String FUNC_LINKWALLET = "linkWallet";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected LinkWallet(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LinkWallet(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LinkWallet(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LinkWallet(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> getUserIdByWalletAddress(String _walletAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERIDBYWALLETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _walletAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getWalletAddressByUserId(String _userId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETWALLETADDRESSBYUSERID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_userId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> linkWallet(String _walletAddress, String _userId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LINKWALLET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _walletAddress), 
                new org.web3j.abi.datatypes.Utf8String(_userId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static LinkWallet load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LinkWallet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LinkWallet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LinkWallet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LinkWallet load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LinkWallet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LinkWallet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LinkWallet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LinkWallet> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(LinkWallet.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<LinkWallet> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LinkWallet.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<LinkWallet> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(LinkWallet.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<LinkWallet> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LinkWallet.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
