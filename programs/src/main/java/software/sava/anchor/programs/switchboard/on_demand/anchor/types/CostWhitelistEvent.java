package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record CostWhitelistEvent(PublicKey[] feeds, int reward) implements Borsh {

  public static CostWhitelistEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeds = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(feeds);
    final var reward = getInt32LE(_data, i);
    return new CostWhitelistEvent(feeds, reward);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(feeds, _data, i);
    putInt32LE(_data, i, reward);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(feeds) + 4;
  }
}
