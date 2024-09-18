package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

// Deprecated: This struct is no longer emitted but is kept to allow parsing of old events.
// Because the event discriminator is based on struct name, it's important to rename the struct if
// fields are changed.
public record InstantUnstakeComponents(boolean instantUnstake,
                                       boolean delinquencyCheck,
                                       boolean commissionCheck,
                                       boolean mevCommissionCheck,
                                       boolean isBlacklisted,
                                       PublicKey voteAccount,
                                       int epoch) implements Borsh {

  public static final int BYTES = 39;

  public static InstantUnstakeComponents read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var instantUnstake = _data[i] == 1;
    ++i;
    final var delinquencyCheck = _data[i] == 1;
    ++i;
    final var commissionCheck = _data[i] == 1;
    ++i;
    final var mevCommissionCheck = _data[i] == 1;
    ++i;
    final var isBlacklisted = _data[i] == 1;
    ++i;
    final var voteAccount = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt16LE(_data, i);
    return new InstantUnstakeComponents(instantUnstake,
                                        delinquencyCheck,
                                        commissionCheck,
                                        mevCommissionCheck,
                                        isBlacklisted,
                                        voteAccount,
                                        epoch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (instantUnstake ? 1 : 0);
    ++i;
    _data[i] = (byte) (delinquencyCheck ? 1 : 0);
    ++i;
    _data[i] = (byte) (commissionCheck ? 1 : 0);
    ++i;
    _data[i] = (byte) (mevCommissionCheck ? 1 : 0);
    ++i;
    _data[i] = (byte) (isBlacklisted ? 1 : 0);
    ++i;
    voteAccount.write(_data, i);
    i += 32;
    putInt16LE(_data, i, epoch);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
