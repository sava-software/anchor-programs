package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LaunchCompletedEvent(CommonFields common,
                                   PublicKey launch,
                                   LaunchState finalState,
                                   long totalCommitted,
                                   PublicKey dao,
                                   PublicKey daoTreasury) implements Borsh {

  public static LaunchCompletedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var launch = readPubKey(_data, i);
    i += 32;
    final var finalState = LaunchState.read(_data, i);
    i += Borsh.len(finalState);
    final var totalCommitted = getInt64LE(_data, i);
    i += 8;
    final var dao = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (dao != null) {
      i += 32;
    }
    final var daoTreasury = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new LaunchCompletedEvent(common,
                                    launch,
                                    finalState,
                                    totalCommitted,
                                    dao,
                                    daoTreasury);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    launch.write(_data, i);
    i += 32;
    i += Borsh.write(finalState, _data, i);
    putInt64LE(_data, i, totalCommitted);
    i += 8;
    i += Borsh.writeOptional(dao, _data, i);
    i += Borsh.writeOptional(daoTreasury, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common)
         + 32
         + Borsh.len(finalState)
         + 8
         + (dao == null ? 1 : (1 + 32))
         + (daoTreasury == null ? 1 : (1 + 32));
  }
}
