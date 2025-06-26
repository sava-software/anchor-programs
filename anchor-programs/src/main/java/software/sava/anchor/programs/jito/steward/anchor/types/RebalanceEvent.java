package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RebalanceEvent(PublicKey voteAccount,
                             int epoch,
                             RebalanceTypeTag rebalanceTypeTag,
                             long increaseLamports,
                             DecreaseComponents decreaseComponents) implements Borsh {

  public static final int BYTES = 75;

  public static RebalanceEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var voteAccount = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt16LE(_data, i);
    i += 2;
    final var rebalanceTypeTag = RebalanceTypeTag.read(_data, i);
    i += Borsh.len(rebalanceTypeTag);
    final var increaseLamports = getInt64LE(_data, i);
    i += 8;
    final var decreaseComponents = DecreaseComponents.read(_data, i);
    return new RebalanceEvent(voteAccount,
                              epoch,
                              rebalanceTypeTag,
                              increaseLamports,
                              decreaseComponents);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    voteAccount.write(_data, i);
    i += 32;
    putInt16LE(_data, i, epoch);
    i += 2;
    i += Borsh.write(rebalanceTypeTag, _data, i);
    putInt64LE(_data, i, increaseLamports);
    i += 8;
    i += Borsh.write(decreaseComponents, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
