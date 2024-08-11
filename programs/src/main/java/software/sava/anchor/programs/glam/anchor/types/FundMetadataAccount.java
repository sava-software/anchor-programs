package software.sava.anchor.programs.glam.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record FundMetadataAccount(PublicKey _address,
                                  byte[] discriminator,
                                  PublicKey fundPubkey,
                                  CompanyField[] company,
                                  FundField[] fund,
                                  ShareClassField[][] shareClasses,
                                  FundManagerField[][] fundManagers) implements Borsh {

  public static final int FUND_PUBKEY_OFFSET = 8;
  public static final int COMPANY_OFFSET = 40;

  public static Filter createFundPubkeyFilter(final PublicKey fundPubkey) {
    return Filter.createMemCompFilter(FUND_PUBKEY_OFFSET, fundPubkey);
  }

  public static FundMetadataAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FundMetadataAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FundMetadataAccount> FACTORY = FundMetadataAccount::read;

  public static FundMetadataAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    final byte[] discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length;
    final var fundPubkey = readPubKey(_data, i);
    i += 32;
    final var company = Borsh.readVector(CompanyField.class, CompanyField::read, _data, i);
    i += Borsh.len(company);
    final var fund = Borsh.readVector(FundField.class, FundField::read, _data, i);
    i += Borsh.len(fund);
    final var shareClasses = Borsh.readMultiDimensionVector(ShareClassField.class, ShareClassField::read, _data, i);
    i += Borsh.len(shareClasses);
    final var fundManagers = Borsh.readMultiDimensionVector(FundManagerField.class, FundManagerField::read, _data, i);
    return new FundMetadataAccount(_address,
                                   discriminator,
                                   fundPubkey,
                                   company,
                                   fund,
                                   shareClasses,
                                   fundManagers);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    System.arraycopy(discriminator, 0, _data, offset, discriminator.length);
    int i = offset + discriminator.length;
    fundPubkey.write(_data, i);
    i += 32;
    i += Borsh.write(company, _data, i);
    i += Borsh.write(fund, _data, i);
    i += Borsh.write(shareClasses, _data, i);
    i += Borsh.write(fundManagers, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + Borsh.len(company)
         + Borsh.len(fund)
         + Borsh.len(shareClasses)
         + Borsh.len(fundManagers);
  }
}
