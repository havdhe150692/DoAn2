pragma solidity ^0.8.13;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract ToadKingToken is ERC20 {
    constructor() ERC20("ToadKingToken", "TKT") {
        _mint(msg.sender, 100000000 * 10 ** decimals());
    }
}