package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Gives us an audit trail of who and what was claimed; also enforces and only-once claim by any party.
public record ClaimStatus(PublicKey _address,
                          Discriminator discriminator,
                          // If true, the tokens have been claimed.
                          boolean isClaimed,
                          // Authority that claimed the tokens. Allows for delegated rewards claiming.
                          PublicKey claimant,
                          // The payer who created the claim.
                          PublicKey claimStatusPayer,
                          // When the funds were claimed.
                          long slotClaimedAt,
                          // Amount of funds claimed.
                          long amount,
                          // The epoch (upto and including) that tip funds can be claimed.
                          // Copied since TDA can be closed, need to track to avoid making multiple claims
                          long expiresAt,
                          // The bump used to generate this account
                          int bump) implements Borsh {

  public static final int BYTES = 98;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(22, 183, 249, 157, 247, 95, 150, 96);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int IS_CLAIMED_OFFSET = 8;
  public static final int CLAIMANT_OFFSET = 9;
  public static final int CLAIM_STATUS_PAYER_OFFSET = 41;
  public static final int SLOT_CLAIMED_AT_OFFSET = 73;
  public static final int AMOUNT_OFFSET = 81;
  public static final int EXPIRES_AT_OFFSET = 89;
  public static final int BUMP_OFFSET = 97;

  public static Filter createIsClaimedFilter(final boolean isClaimed) {
    return Filter.createMemCompFilter(IS_CLAIMED_OFFSET, new byte[]{(byte) (isClaimed ? 1 : 0)});
  }

  public static Filter createClaimantFilter(final PublicKey claimant) {
    return Filter.createMemCompFilter(CLAIMANT_OFFSET, claimant);
  }

  public static Filter createClaimStatusPayerFilter(final PublicKey claimStatusPayer) {
    return Filter.createMemCompFilter(CLAIM_STATUS_PAYER_OFFSET, claimStatusPayer);
  }

  public static Filter createSlotClaimedAtFilter(final long slotClaimedAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotClaimedAt);
    return Filter.createMemCompFilter(SLOT_CLAIMED_AT_OFFSET, _data);
  }

  public static Filter createAmountFilter(final long amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, amount);
    return Filter.createMemCompFilter(AMOUNT_OFFSET, _data);
  }

  public static Filter createExpiresAtFilter(final long expiresAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, expiresAt);
    return Filter.createMemCompFilter(EXPIRES_AT_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static ClaimStatus read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ClaimStatus read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ClaimStatus read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ClaimStatus> FACTORY = ClaimStatus::read;

  public static ClaimStatus read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var isClaimed = _data[i] == 1;
    ++i;
    final var claimant = readPubKey(_data, i);
    i += 32;
    final var claimStatusPayer = readPubKey(_data, i);
    i += 32;
    final var slotClaimedAt = getInt64LE(_data, i);
    i += 8;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var expiresAt = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    return new ClaimStatus(_address,
                           discriminator,
                           isClaimed,
                           claimant,
                           claimStatusPayer,
                           slotClaimedAt,
                           amount,
                           expiresAt,
                           bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) (isClaimed ? 1 : 0);
    ++i;
    claimant.write(_data, i);
    i += 32;
    claimStatusPayer.write(_data, i);
    i += 32;
    putInt64LE(_data, i, slotClaimedAt);
    i += 8;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, expiresAt);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
