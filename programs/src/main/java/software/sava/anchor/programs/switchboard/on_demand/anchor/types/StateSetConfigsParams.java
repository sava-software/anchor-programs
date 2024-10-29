package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record StateSetConfigsParams(PublicKey newAuthority,
                                    int testOnlyDisableMrEnclaveCheck,
                                    PublicKey stakePool,
                                    PublicKey stakeProgram,
                                    int addAdvisory,
                                    int rmAdvisory,
                                    int epochLength,
                                    boolean resetEpochs,
                                    PublicKey switchMint,
                                    int enableStaking,
                                    int subsidyAmount,
                                    int baseReward,
                                    PublicKey addCostWl,
                                    PublicKey rmCostWl) implements Borsh {

  public static final int BYTES = 211;

  public static StateSetConfigsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var newAuthority = readPubKey(_data, i);
    i += 32;
    final var testOnlyDisableMrEnclaveCheck = _data[i] & 0xFF;
    ++i;
    final var stakePool = readPubKey(_data, i);
    i += 32;
    final var stakeProgram = readPubKey(_data, i);
    i += 32;
    final var addAdvisory = getInt16LE(_data, i);
    i += 2;
    final var rmAdvisory = getInt16LE(_data, i);
    i += 2;
    final var epochLength = getInt32LE(_data, i);
    i += 4;
    final var resetEpochs = _data[i] == 1;
    ++i;
    final var switchMint = readPubKey(_data, i);
    i += 32;
    final var enableStaking = _data[i] & 0xFF;
    ++i;
    final var subsidyAmount = getInt32LE(_data, i);
    i += 4;
    final var baseReward = getInt32LE(_data, i);
    i += 4;
    final var addCostWl = readPubKey(_data, i);
    i += 32;
    final var rmCostWl = readPubKey(_data, i);
    return new StateSetConfigsParams(newAuthority,
                                     testOnlyDisableMrEnclaveCheck,
                                     stakePool,
                                     stakeProgram,
                                     addAdvisory,
                                     rmAdvisory,
                                     epochLength,
                                     resetEpochs,
                                     switchMint,
                                     enableStaking,
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
    stakePool.write(_data, i);
    i += 32;
    stakeProgram.write(_data, i);
    i += 32;
    putInt16LE(_data, i, addAdvisory);
    i += 2;
    putInt16LE(_data, i, rmAdvisory);
    i += 2;
    putInt32LE(_data, i, epochLength);
    i += 4;
    _data[i] = (byte) (resetEpochs ? 1 : 0);
    ++i;
    switchMint.write(_data, i);
    i += 32;
    _data[i] = (byte) enableStaking;
    ++i;
    putInt32LE(_data, i, subsidyAmount);
    i += 4;
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
