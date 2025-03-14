package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StateSetConfigsParams(PublicKey newAuthority,
                                    int testOnlyDisableMrEnclaveCheck,
                                    int addAdvisory,
                                    int rmAdvisory,
                                    PublicKey switchMint,
                                    long subsidyAmount,
                                    int baseReward,
                                    PublicKey addCostWl,
                                    PublicKey rmCostWl) implements Borsh {

  public static final int BYTES = 145;

  public static StateSetConfigsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var newAuthority = readPubKey(_data, i);
    i += 32;
    final var testOnlyDisableMrEnclaveCheck = _data[i] & 0xFF;
    ++i;
    final var addAdvisory = getInt16LE(_data, i);
    i += 2;
    final var rmAdvisory = getInt16LE(_data, i);
    i += 2;
    final var switchMint = readPubKey(_data, i);
    i += 32;
    final var subsidyAmount = getInt64LE(_data, i);
    i += 8;
    final var baseReward = getInt32LE(_data, i);
    i += 4;
    final var addCostWl = readPubKey(_data, i);
    i += 32;
    final var rmCostWl = readPubKey(_data, i);
    return new StateSetConfigsParams(newAuthority,
                                     testOnlyDisableMrEnclaveCheck,
                                     addAdvisory,
                                     rmAdvisory,
                                     switchMint,
                                     subsidyAmount,
                                     baseReward,
                                     addCostWl,
                                     rmCostWl);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    newAuthority.write(_data, i);
    i += 32;
    _data[i] = (byte) testOnlyDisableMrEnclaveCheck;
    ++i;
    putInt16LE(_data, i, addAdvisory);
    i += 2;
    putInt16LE(_data, i, rmAdvisory);
    i += 2;
    switchMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, subsidyAmount);
    i += 8;
    putInt32LE(_data, i, baseReward);
    i += 4;
    addCostWl.write(_data, i);
    i += 32;
    rmCostWl.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
