package com.example.doan2.chain.smartcontract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class ToadKingMarketplace extends Contract {
    public static final String BINARY = "6080604052600160065560646007553480156200001b57600080fd5b5060405162002d0b38038062002d0b83398181016040528101906200004191906200022b565b6001600081905550620000696200005d620000f360201b60201c565b620000fb60201b60201c565b81600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505062000272565b600033905090565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000620001f382620001c6565b9050919050565b6200020581620001e6565b81146200021157600080fd5b50565b6000815190506200022581620001fa565b92915050565b60008060408385031215620002455762000244620001c1565b5b6000620002558582860162000214565b9250506020620002688582860162000214565b9150509250929050565b612a8980620002826000396000f3fe6080604052600436106100c25760003560e01c8063715018a61161007f578063d34c127e11610059578063d34c127e14610237578063f2fde38b14610274578063f8a8fd6d1461029d578063fe8723e1146102b4576100c2565b8063715018a6146101b85780638da5cb5b146101cf578063c7be7a49146101fa576100c2565b8063243adbdd146100c7578063443c2fd7146100e3578063490624931461010e5780634cb558761461013957806359f48271146101645780635dc3ffa81461018d575b600080fd5b6100e160048036038101906100dc9190611cfd565b6102dd565b005b3480156100ef57600080fd5b506100f8610a53565b6040516101059190611ebf565b60405180910390f35b34801561011a57600080fd5b50610123610c6f565b6040516101309190611f40565b60405180910390f35b34801561014557600080fd5b5061014e610c95565b60405161015b9190611ebf565b60405180910390f35b34801561017057600080fd5b5061018b60048036038101906101869190611cfd565b610fee565b005b34801561019957600080fd5b506101a26111df565b6040516101af9190611f7c565b60405180910390f35b3480156101c457600080fd5b506101cd611205565b005b3480156101db57600080fd5b506101e4611219565b6040516101f19190611fb8565b60405180910390f35b34801561020657600080fd5b50610221600480360381019061021c9190611cfd565b611243565b60405161022e919061204e565b60405180910390f35b34801561024357600080fd5b5061025e60048036038101906102599190611cfd565b6113e6565b60405161026b9190611ebf565b60405180910390f35b34801561028057600080fd5b5061029b60048036038101906102969190612095565b6116ab565b005b3480156102a957600080fd5b506102b261172e565b005b3480156102c057600080fd5b506102db60048036038101906102d691906120c2565b611772565b005b8060008111610321576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103189061215f565b60405180910390fd5b61032b6004611aee565b81111561036d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610364906121cb565b60405180910390fd5b6000600860008481526020019081526020016000209050600115158160040160149054906101000a900460ff161515146103dc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103d390612237565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff168160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff160361046e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610465906122a3565b60405180910390fd5b60008160020154905060006007546006548361048a91906122f2565b6104949190612363565b90506000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231336040518263ffffffff1660e01b81526004016104f39190611fb8565b602060405180830381865afa158015610510573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061053491906123a9565b905082811015610579576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161057090612448565b60405180910390fd5b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dd62ed3e33306040518363ffffffff1660e01b81526004016105d8929190612468565b602060405180830381865afa1580156105f5573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061061991906123a9565b90508381101561065e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161065590612503565b60405180910390fd5b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3330876040518463ffffffff1660e01b81526004016106bd93929190612532565b6020604051808303816000875af11580156106dc573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107009190612595565b50600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb8660030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16858761077091906125c2565b6040518363ffffffff1660e01b815260040161078d929190612617565b6020604051808303816000875af11580156107ac573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107d09190612595565b50600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb610817611219565b856040518363ffffffff1660e01b8152600401610835929190612640565b6020604051808303816000875af1158015610854573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108789190612595565b50338560040160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506108c66005611afc565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd303388600101546040518463ffffffff1660e01b815260040161092993929190612532565b600060405180830381600087803b15801561094357600080fd5b505af1158015610957573d6000803e3d6000fd5b5050505060008560040160146101000a81548160ff021916908315150217905550338560040160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507ff198f3fc4cf3c5300e58bde8cf782caf44a5af3b7c522b38ca5201a55cfb9ba6856000015486600101548760030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168860040160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1688604051610a42959493929190612669565b60405180910390a150505050505050565b60606000610a616004611aee565b90506000610a6f6005611aee565b82610a7a91906125c2565b905060008167ffffffffffffffff811115610a9857610a976126bc565b5b604051908082528060200260200182016040528015610ad157816020015b610abe611c5e565b815260200190600190039081610ab65790505b5090506000805b84811015610c645760086000600183610af191906126eb565b815260200190815260200160002060040160149054906101000a900460ff1615610c515760086000600183610b2691906126eb565b81526020019081526020016000206040518060c00160405290816000820154815260200160018201548152602001600282015481526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160149054906101000a900460ff161515151581525050838381518110610c3757610c3661271f565b5b60200260200101819052508180610c4d9061274e565b9250505b8080610c5c9061274e565b915050610ad8565b508194505050505090565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60606000610ca36004611aee565b90506000805b82811015610d81573373ffffffffffffffffffffffffffffffffffffffff1660086000600184610cd991906126eb565b815260200190815260200160002060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16148015610d5a575060086000600183610d3a91906126eb565b815260200190815260200160002060040160149054906101000a900460ff165b15610d6e578180610d6a9061274e565b9250505b8080610d799061274e565b915050610ca9565b5060008167ffffffffffffffff811115610d9e57610d9d6126bc565b5b604051908082528060200260200182016040528015610dd757816020015b610dc4611c5e565b815260200190600190039081610dbc5790505b5090506000805b84811015610fe3573373ffffffffffffffffffffffffffffffffffffffff1660086000600184610e0e91906126eb565b815260200190815260200160002060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16148015610e8f575060086000600183610e6f91906126eb565b815260200190815260200160002060040160149054906101000a900460ff165b15610fd05760086000600183610ea591906126eb565b81526020019081526020016000206040518060c00160405290816000820154815260200160018201548152602001600282015481526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160149054906101000a900460ff161515151581525050838381518110610fb657610fb561271f565b5b60200260200101819052508180610fcc9061274e565b9250505b8080610fdb9061274e565b915050610dde565b508194505050505090565b8060008111611032576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016110299061215f565b60405180910390fd5b61103c6004611aee565b81111561107e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611075906121cb565b60405180910390fd5b600060086000848152602001908152602001600020905060008160040160146101000a81548160ff021916908315150217905550600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd303384600101546040518463ffffffff1660e01b815260040161111593929190612532565b600060405180830381600087803b15801561112f57600080fd5b505af1158015611143573d6000803e3d6000fd5b505050507f39f8abe555a612ad2c831a5cf89e38bb205a6cec70f983e528c69734a4d017a0816000015482600101548360030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168460040160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1685600201546040516111d2959493929190612669565b60405180910390a1505050565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b61120d611b12565b6112176000611b90565b565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b61124b611c5e565b816000811161128f576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016112869061215f565b60405180910390fd5b6112996004611aee565b8111156112db576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016112d2906121cb565b60405180910390fd5b600860008481526020019081526020016000206040518060c00160405290816000820154815260200160018201548152602001600282015481526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160149054906101000a900460ff161515151581525050915050919050565b606060006113f46004611aee565b90506000805b8281101561148757846008600060018461141491906126eb565b81526020019081526020016000206001015414801561146057506008600060018361143f91906126eb565b815260200190815260200160002060040160149054906101000a900460ff16155b156114745781806114709061274e565b9250505b808061147f9061274e565b9150506113fa565b5060008167ffffffffffffffff8111156114a4576114a36126bc565b5b6040519080825280602002602001820160405280156114dd57816020015b6114ca611c5e565b8152602001906001900390816114c25790505b5090506000805b8481101561169e5786600860006001846114fe91906126eb565b81526020019081526020016000206001015414801561154a57506008600060018361152991906126eb565b815260200190815260200160002060040160149054906101000a900460ff16155b1561168b576008600060018361156091906126eb565b81526020019081526020016000206040518060c00160405290816000820154815260200160018201548152602001600282015481526020016003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016004820160149054906101000a900460ff1615151515815250508383815181106116715761167061271f565b5b602002602001018190525081806116879061274e565b9250505b80806116969061274e565b9150506114e4565b5081945050505050919050565b6116b3611b12565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603611722576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161171990612808565b60405180910390fd5b61172b81611b90565b50565b7f59c618f7b9931e72dbed2c94e4630716a0127b95d1c4edc81210be6092633f3733611758611219565b3060405161176893929190612828565b60405180910390a1565b813373ffffffffffffffffffffffffffffffffffffffff16600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636352211e836040518263ffffffff1660e01b81526004016117e5919061285f565b602060405180830381865afa158015611802573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190611826919061288f565b73ffffffffffffffffffffffffffffffffffffffff161461187c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161187390612908565b60405180910390fd5b81600081116118c0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016118b790612974565b60405180910390fd5b6118ca6004611afc565b60006118d66004611aee565b90506040518060c001604052808281526020018681526020018581526020013373ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600115158152506008600083815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160040160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060a08201518160040160146101000a81548160ff021916908315150217905550905050600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3330886040518463ffffffff1660e01b8152600401611a7693929190612532565b600060405180830381600087803b158015611a9057600080fd5b505af1158015611aa4573d6000803e3d6000fd5b505050507fb7c1e5fc292762b6879414351a4c0e1bb8d1633c5743c6d632ec5275943f59c68186333088604051611adf959493929190612994565b60405180910390a15050505050565b600081600001549050919050565b6001816000016000828254019250508190555050565b611b1a611c56565b73ffffffffffffffffffffffffffffffffffffffff16611b38611219565b73ffffffffffffffffffffffffffffffffffffffff1614611b8e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611b8590612a33565b60405180910390fd5b565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600033905090565b6040518060c00160405280600081526020016000815260200160008152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff1681526020016000151581525090565b600080fd5b6000819050919050565b611cda81611cc7565b8114611ce557600080fd5b50565b600081359050611cf781611cd1565b92915050565b600060208284031215611d1357611d12611cc2565b5b6000611d2184828501611ce8565b91505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b611d5f81611cc7565b82525050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611d9082611d65565b9050919050565b611da081611d85565b82525050565b60008115159050919050565b611dbb81611da6565b82525050565b60c082016000820151611dd76000850182611d56565b506020820151611dea6020850182611d56565b506040820151611dfd6040850182611d56565b506060820151611e106060850182611d97565b506080820151611e236080850182611d97565b5060a0820151611e3660a0850182611db2565b50505050565b6000611e488383611dc1565b60c08301905092915050565b6000602082019050919050565b6000611e6c82611d2a565b611e768185611d35565b9350611e8183611d46565b8060005b83811015611eb2578151611e998882611e3c565b9750611ea483611e54565b925050600181019050611e85565b5085935050505092915050565b60006020820190508181036000830152611ed98184611e61565b905092915050565b6000819050919050565b6000611f06611f01611efc84611d65565b611ee1565b611d65565b9050919050565b6000611f1882611eeb565b9050919050565b6000611f2a82611f0d565b9050919050565b611f3a81611f1f565b82525050565b6000602082019050611f556000830184611f31565b92915050565b6000611f6682611f0d565b9050919050565b611f7681611f5b565b82525050565b6000602082019050611f916000830184611f6d565b92915050565b6000611fa282611d65565b9050919050565b611fb281611f97565b82525050565b6000602082019050611fcd6000830184611fa9565b92915050565b60c082016000820151611fe96000850182611d56565b506020820151611ffc6020850182611d56565b50604082015161200f6040850182611d56565b5060608201516120226060850182611d97565b5060808201516120356080850182611d97565b5060a082015161204860a0850182611db2565b50505050565b600060c0820190506120636000830184611fd3565b92915050565b61207281611f97565b811461207d57600080fd5b50565b60008135905061208f81612069565b92915050565b6000602082840312156120ab576120aa611cc2565b5b60006120b984828501612080565b91505092915050565b600080604083850312156120d9576120d8611cc2565b5b60006120e785828601611ce8565b92505060206120f885828601611ce8565b9150509250929050565b600082825260208201905092915050565b7f4d61726b65744974656d49642073686f756c64206265203e2030000000000000600082015250565b6000612149601a83612102565b915061215482612113565b602082019050919050565b600060208201905081810360008301526121788161213c565b9050919050565b7f4d61726b65744974656d49642073686f756c6420626520657869737400000000600082015250565b60006121b5601c83612102565b91506121c08261217f565b602082019050919050565b600060208201905081810360008301526121e4816121a8565b9050919050565b7f4e4654206973206e6f742053656c6c696e670000000000000000000000000000600082015250565b6000612221601283612102565b915061222c826121eb565b602082019050919050565b6000602082019050818103600083015261225081612214565b9050919050565b7f596f752063616e206e6f742062757920796f7572206f776e204e465400000000600082015250565b600061228d601c83612102565b915061229882612257565b602082019050919050565b600060208201905081810360008301526122bc81612280565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006122fd82611cc7565b915061230883611cc7565b925082820261231681611cc7565b9150828204841483151761232d5761232c6122c3565b5b5092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b600061236e82611cc7565b915061237983611cc7565b92508261238957612388612334565b5b828204905092915050565b6000815190506123a381611cd1565b92915050565b6000602082840312156123bf576123be611cc2565b5b60006123cd84828501612394565b91505092915050565b7f596f7520646f206e6f74206861766520656e6f75676820544b5420746f20627560008201527f792074686973204e465400000000000000000000000000000000000000000000602082015250565b6000612432602a83612102565b915061243d826123d6565b604082019050919050565b6000602082019050818103600083015261246181612425565b9050919050565b600060408201905061247d6000830185611fa9565b61248a6020830184611fa9565b9392505050565b7f596f7520646f206e6f7420617070726f766520656e6f75676820544b5420746f60008201527f206275792074686973204e465400000000000000000000000000000000000000602082015250565b60006124ed602d83612102565b91506124f882612491565b604082019050919050565b6000602082019050818103600083015261251c816124e0565b9050919050565b61252c81611cc7565b82525050565b60006060820190506125476000830186611fa9565b6125546020830185611fa9565b6125616040830184612523565b949350505050565b61257281611da6565b811461257d57600080fd5b50565b60008151905061258f81612569565b92915050565b6000602082840312156125ab576125aa611cc2565b5b60006125b984828501612580565b91505092915050565b60006125cd82611cc7565b91506125d883611cc7565b92508282039050818111156125f0576125ef6122c3565b5b92915050565b600061260182611f0d565b9050919050565b612611816125f6565b82525050565b600060408201905061262c6000830185612608565b6126396020830184612523565b9392505050565b60006040820190506126556000830185611fa9565b6126626020830184612523565b9392505050565b600060a08201905061267e6000830188612523565b61268b6020830187612523565b6126986040830186612608565b6126a56060830185612608565b6126b26080830184612523565b9695505050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b60006126f682611cc7565b915061270183611cc7565b9250828201905080821115612719576127186122c3565b5b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600061275982611cc7565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361278b5761278a6122c3565b5b600182019050919050565b7f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160008201527f6464726573730000000000000000000000000000000000000000000000000000602082015250565b60006127f2602683612102565b91506127fd82612796565b604082019050919050565b60006020820190508181036000830152612821816127e5565b9050919050565b600060608201905061283d6000830186611fa9565b61284a6020830185611fa9565b6128576040830184611fa9565b949350505050565b60006020820190506128746000830184612523565b92915050565b60008151905061288981612069565b92915050565b6000602082840312156128a5576128a4611cc2565b5b60006128b38482850161287a565b91505092915050565b7f596f7520617265206e6f74204e46542773206f776e6572000000000000000000600082015250565b60006128f2601783612102565b91506128fd826128bc565b602082019050919050565b60006020820190508181036000830152612921816128e5565b9050919050565b7f5072696365206d757374203e2030000000000000000000000000000000000000600082015250565b600061295e600e83612102565b915061296982612928565b602082019050919050565b6000602082019050818103600083015261298d81612951565b9050919050565b600060a0820190506129a96000830188612523565b6129b66020830187612523565b6129c36040830186611fa9565b6129d06060830185611fa9565b6129dd6080830184612523565b9695505050505050565b7f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572600082015250565b6000612a1d602083612102565b9150612a28826129e7565b602082019050919050565b60006020820190508181036000830152612a4c81612a10565b905091905056fea26469706673582212204eeda9c65ad80871be953ec65aa7e3105a12d504577ab0b7bf1b0924b68041e364736f6c63430008110033";

    public static final String FUNC_BUYNFT = "buyNft";

    public static final String FUNC_CANCELSELLNFT = "cancelSellNft";

    public static final String FUNC_GETLISTINGNFTS = "getListingNfts";

    public static final String FUNC_GETMARKETITEM = "getMarketItem";

    public static final String FUNC_GETMYLISTINGNFTS = "getMyListingNfts";

    public static final String FUNC_GETNFTSELLHISTORY = "getNftSellHistory";

    public static final String FUNC_LISTNFT = "listNft";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TEST = "test";

    public static final String FUNC_TOADKINGCOIN = "toadKingCoin";

    public static final String FUNC_TOADKINGNFT = "toadKingNFT";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event LOGGING_EVENT = new Event("Logging",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TOADNFTLISTED_EVENT = new Event("ToadNFTListed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TOADNFTSALECANCELED_EVENT = new Event("ToadNFTSaleCanceled",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TOADNFTSOLD_EVENT = new Event("ToadNFTSold",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected ToadKingMarketplace(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ToadKingMarketplace(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ToadKingMarketplace(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ToadKingMarketplace(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<LoggingEventResponse> getLoggingEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGGING_EVENT, transactionReceipt);
        ArrayList<LoggingEventResponse> responses = new ArrayList<LoggingEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LoggingEventResponse typedResponse = new LoggingEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.a = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.b = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.c = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LoggingEventResponse> loggingEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LoggingEventResponse>() {
            @Override
            public LoggingEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGGING_EVENT, log);
                LoggingEventResponse typedResponse = new LoggingEventResponse();
                typedResponse.log = log;
                typedResponse.a = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.b = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.c = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LoggingEventResponse> loggingEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGGING_EVENT));
        return loggingEventFlowable(filter);
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

    public static List<ToadNFTListedEventResponse> getToadNFTListedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOADNFTLISTED_EVENT, transactionReceipt);
        ArrayList<ToadNFTListedEventResponse> responses = new ArrayList<ToadNFTListedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ToadNFTListedEventResponse typedResponse = new ToadNFTListedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ToadNFTListedEventResponse> toadNFTListedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ToadNFTListedEventResponse>() {
            @Override
            public ToadNFTListedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOADNFTLISTED_EVENT, log);
                ToadNFTListedEventResponse typedResponse = new ToadNFTListedEventResponse();
                typedResponse.log = log;
                typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ToadNFTListedEventResponse> toadNFTListedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOADNFTLISTED_EVENT));
        return toadNFTListedEventFlowable(filter);
    }

    public static List<ToadNFTSaleCanceledEventResponse> getToadNFTSaleCanceledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOADNFTSALECANCELED_EVENT, transactionReceipt);
        ArrayList<ToadNFTSaleCanceledEventResponse> responses = new ArrayList<ToadNFTSaleCanceledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ToadNFTSaleCanceledEventResponse typedResponse = new ToadNFTSaleCanceledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ToadNFTSaleCanceledEventResponse> toadNFTSaleCanceledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ToadNFTSaleCanceledEventResponse>() {
            @Override
            public ToadNFTSaleCanceledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOADNFTSALECANCELED_EVENT, log);
                ToadNFTSaleCanceledEventResponse typedResponse = new ToadNFTSaleCanceledEventResponse();
                typedResponse.log = log;
                typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ToadNFTSaleCanceledEventResponse> toadNFTSaleCanceledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOADNFTSALECANCELED_EVENT));
        return toadNFTSaleCanceledEventFlowable(filter);
    }

    public static List<ToadNFTSoldEventResponse> getToadNFTSoldEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOADNFTSOLD_EVENT, transactionReceipt);
        ArrayList<ToadNFTSoldEventResponse> responses = new ArrayList<ToadNFTSoldEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ToadNFTSoldEventResponse typedResponse = new ToadNFTSoldEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ToadNFTSoldEventResponse> toadNFTSoldEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ToadNFTSoldEventResponse>() {
            @Override
            public ToadNFTSoldEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOADNFTSOLD_EVENT, log);
                ToadNFTSoldEventResponse typedResponse = new ToadNFTSoldEventResponse();
                typedResponse.log = log;
                typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ToadNFTSoldEventResponse> toadNFTSoldEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOADNFTSOLD_EVENT));
        return toadNFTSoldEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> buyNft(BigInteger _marketItemId, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYNFT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_marketItemId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelSellNft(BigInteger _marketItemId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELSELLNFT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_marketItemId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getListingNfts() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETLISTINGNFTS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<ToadNFTMarket>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<ToadNFTMarket> getMarketItem(BigInteger _marketItemId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMARKETITEM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_marketItemId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<ToadNFTMarket>() {}));
        return executeRemoteCallSingleValueReturn(function, ToadNFTMarket.class);
    }

    public RemoteFunctionCall<List> getMyListingNfts() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYLISTINGNFTS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<ToadNFTMarket>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getNftSellHistory(BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNFTSELLHISTORY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<ToadNFTMarket>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> listNft(BigInteger _tokenId, BigInteger _price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LISTNFT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId),
                        new org.web3j.abi.datatypes.generated.Uint256(_price)),
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

    public RemoteFunctionCall<TransactionReceipt> test() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TEST,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> toadKingCoin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOADKINGCOIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> toadKingNFT() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOADKINGNFT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ToadKingMarketplace load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ToadKingMarketplace(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ToadKingMarketplace load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ToadKingMarketplace(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ToadKingMarketplace load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ToadKingMarketplace(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ToadKingMarketplace load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ToadKingMarketplace(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ToadKingMarketplace> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _toadKingTokenContract, String _toadKingNFTContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _toadKingTokenContract),
                new org.web3j.abi.datatypes.Address(160, _toadKingNFTContract)));
        return deployRemoteCall(ToadKingMarketplace.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ToadKingMarketplace> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _toadKingTokenContract, String _toadKingNFTContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _toadKingTokenContract),
                new org.web3j.abi.datatypes.Address(160, _toadKingNFTContract)));
        return deployRemoteCall(ToadKingMarketplace.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ToadKingMarketplace> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _toadKingTokenContract, String _toadKingNFTContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _toadKingTokenContract),
                new org.web3j.abi.datatypes.Address(160, _toadKingNFTContract)));
        return deployRemoteCall(ToadKingMarketplace.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ToadKingMarketplace> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _toadKingTokenContract, String _toadKingNFTContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _toadKingTokenContract),
                new org.web3j.abi.datatypes.Address(160, _toadKingNFTContract)));
        return deployRemoteCall(ToadKingMarketplace.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ToadNFTMarket extends StaticStruct {
        public BigInteger itemId;

        public BigInteger tokenId;

        public BigInteger price;

        public String seller;

        public String buyer;

        public Boolean isSelling;

        public ToadNFTMarket(BigInteger itemId, BigInteger tokenId, BigInteger price, String seller, String buyer, Boolean isSelling) {
            super(new org.web3j.abi.datatypes.generated.Uint256(itemId),
                    new org.web3j.abi.datatypes.generated.Uint256(tokenId),
                    new org.web3j.abi.datatypes.generated.Uint256(price),
                    new org.web3j.abi.datatypes.Address(160, seller),
                    new org.web3j.abi.datatypes.Address(160, buyer),
                    new org.web3j.abi.datatypes.Bool(isSelling));
            this.itemId = itemId;
            this.tokenId = tokenId;
            this.price = price;
            this.seller = seller;
            this.buyer = buyer;
            this.isSelling = isSelling;
        }

        public ToadNFTMarket(Uint256 itemId, Uint256 tokenId, Uint256 price, Address seller, Address buyer, Bool isSelling) {
            super(itemId, tokenId, price, seller, buyer, isSelling);
            this.itemId = itemId.getValue();
            this.tokenId = tokenId.getValue();
            this.price = price.getValue();
            this.seller = seller.getValue();
            this.buyer = buyer.getValue();
            this.isSelling = isSelling.getValue();
        }

        public String toString()
        {
            return this.itemId + " " + this.price + " " + this.tokenId + " " + this.buyer + " " + this.seller + " " + this.isSelling;
        }
    }

    public static class LoggingEventResponse extends BaseEventResponse {
        public String a;

        public String b;

        public String c;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class ToadNFTListedEventResponse extends BaseEventResponse {
        public BigInteger itemId;

        public BigInteger tokenId;

        public String seller;

        public String buyer;

        public BigInteger price;
    }

    public static class ToadNFTSaleCanceledEventResponse extends BaseEventResponse {
        public BigInteger itemId;

        public BigInteger tokenId;

        public String seller;

        public String buyer;

        public BigInteger price;
    }

    public static class ToadNFTSoldEventResponse extends BaseEventResponse {
        public BigInteger itemId;

        public BigInteger tokenId;

        public String seller;

        public String buyer;

        public BigInteger price;
    }
}