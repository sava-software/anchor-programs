package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OperatorVote(PublicKey operator,
                           long slotVoted,
                           StakeWeights stakeWeights,
                           int ballotIndex,
                           byte[] reserved) implements Borsh {

  public static final int BYTES = 250;
  public static final int RESERVED_LEN = 64;

  public static OperatorVote read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var operator = readPubKey(_data, i);
    i += 32;
    final var slotVoted = getInt64LE(_data, i);
    i += 8;
    final var stakeWeights = StakeWeights.read(_data, i);
    i += Borsh.len(stakeWeights);
    final var ballotIndex = getInt16LE(_data, i);
    i += 2;
    final var reserved = new byte[64];
    Borsh.readArray(reserved, _data, i);
    return new OperatorVote(operator,
                            slotVoted,
                            stakeWeights,
                            ballotIndex,
                            reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    operator.write(_data, i);
    i += 32;
    putInt64LE(_data, i, slotVoted);
    i += 8;
    i += Borsh.write(stakeWeights, _data, i);
    putInt16LE(_data, i, ballotIndex);
    i += 2;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
