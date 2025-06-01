package software.sava.anchor.programs.kamino.vaults.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Reserve collateral
public record ReserveCollateral(// Reserve collateral mint address
                                PublicKey mintPubkey,
                                // Reserve collateral mint supply, used for exchange rate
                                long mintTotalSupply,
                                // Reserve collateral supply address
                                PublicKey supplyVault,
                                BigInteger[] padding1,
                                BigInteger[] padding2) implements Borsh {

  public static final int BYTES = 1096;

  public static ReserveCollateral read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mintPubkey = readPubKey(_data, i);
    i += 32;
    final var mintTotalSupply = getInt64LE(_data, i);
    i += 8;
    final var supplyVault = readPubKey(_data, i);
    i += 32;
    final var padding1 = new BigInteger[32];
    i += Borsh.readArray(padding1, _data, i);
    final var padding2 = new BigInteger[32];
    Borsh.readArray(padding2, _data, i);
    return new ReserveCollateral(mintPubkey,
                                 mintTotalSupply,
                                 supplyVault,
                                 padding1,
                                 padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mintPubkey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, mintTotalSupply);
    i += 8;
    supplyVault.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.writeArray(padding2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
