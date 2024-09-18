package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record InstantUnstakeComponentsV2(// Aggregate of all checks
                                         boolean instantUnstake,
                                         // Checks if validator has missed > instant_unstake_delinquency_threshold_ratio of votes this epoch
                                         boolean delinquencyCheck,
                                         // Checks if validator has increased commission > commission_threshold
                                         boolean commissionCheck,
                                         // Checks if validator has increased MEV commission > mev_commission_bps_threshold
                                         boolean mevCommissionCheck,
                                         // Checks if validator was added to blacklist
                                         boolean isBlacklisted,
                                         PublicKey voteAccount,
                                         int epoch,
                                         // Details about why a given check was calculated
                                         InstantUnstakeDetails details) implements Borsh {

  public static final int BYTES = 70;

  public static InstantUnstakeComponentsV2 read(final byte[] _data, final int offset) {
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
    i += 2;
    final var details = InstantUnstakeDetails.read(_data, i);
    return new InstantUnstakeComponentsV2(instantUnstake,
                                          delinquencyCheck,
                                          commissionCheck,
                                          mevCommissionCheck,
                                          isBlacklisted,
                                          voteAccount,
                                          epoch,
                                          details);
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
    i += Borsh.write(details, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
