package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultOperatorStakeWeight(PublicKey vault,
                                       long vaultIndex,
                                       NcnFeeGroup ncnFeeGroup,
                                       StakeWeights stakeWeight,
                                       byte[] reserved) implements Borsh {

  public static final int BYTES = 217;
  public static final int RESERVED_LEN = 32;

  public static VaultOperatorStakeWeight read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var vaultIndex = getInt64LE(_data, i);
    i += 8;
    final var ncnFeeGroup = NcnFeeGroup.read(_data, i);
    i += Borsh.len(ncnFeeGroup);
    final var stakeWeight = StakeWeights.read(_data, i);
    i += Borsh.len(stakeWeight);
    final var reserved = new byte[32];
    Borsh.readArray(reserved, _data, i);
    return new VaultOperatorStakeWeight(vault,
                                        vaultIndex,
                                        ncnFeeGroup,
                                        stakeWeight,
                                        reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, vaultIndex);
    i += 8;
    i += Borsh.write(ncnFeeGroup, _data, i);
    i += Borsh.write(stakeWeight, _data, i);
    i += Borsh.writeArrayChecked(reserved, 32, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
